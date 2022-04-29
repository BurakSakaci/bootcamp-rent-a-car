package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.listdto.InvoiceListDto;
import com.turkcell.rentACar.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACar.entities.concrates.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService{
	private final InvoiceDao invoiceDao;
	private final ModelMapperService modelMapperService;
	private final CarService carService;
	private final RentService rentService;
	private final OrderedAdditionalServiceService orderedAdditionalServiceService;
	private final CustomerService customerService;
	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, CarService carService,
			RentService rentService, OrderedAdditionalServiceService orderedAdditionalServiceService, CustomerService customerService) {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.rentService = rentService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.customerService = customerService;
	}

	@Override
	public DataResult<List<InvoiceListDto>> getAll() {

		List<Invoice> result = this.invoiceDao.findAll();

		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetInvoiceDto> getById(int id) throws BusinessException {

		checkIfInvoiceExistsById(id);
		
		Invoice invoice = invoiceDao.getById(id);
		GetInvoiceDto response = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<InvoiceListDto>> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate) {
		
		List<Invoice> result = this.invoiceDao.findByCreationDateBetween(fromDate, toDate);
		
		List<InvoiceListDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.InvoiceMessages.INVOICES_BETWEEN_DATES_LISTED);
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkIfInvoiceExistsById(id);
		
		this.invoiceDao.deleteById(id);

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		
		checkIfInvoiceExistsById(updateInvoiceRequest.getInvoiceId());
		
		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		
		invoice.setCustomer(getCustomerById(updateInvoiceRequest.getCustomerId()));
		invoiceManuelMapping(invoice);
		
		
		this.invoiceDao.save(invoice);

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public DataResult<Invoice> invoiceCreator(Rent rent) {
		Invoice invoice = invoiceCreatorMapper(rent);
		invoice.setExtraAmount(serviceAmount(rent) + cityExtraAmount(rent));
		invoice.setTotalAmount(serviceAmount(rent) + carRentalAmount(rent) + cityExtraAmount(rent));

		this.invoiceDao.saveAndFlush(invoice);

		return new SuccessDataResult<Invoice>(invoice, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public DataResult<Invoice> invoiceCreatorForEndRent(Rent rent) {
		Invoice invoice = invoiceCreatorMapper(rent);
		invoice.setExtraAmount(serviceAmount(rent));
		invoice.setTotalAmount(serviceAmount(rent) + carRentalAmount(rent));

		this.invoiceDao.saveAndFlush(invoice);

		return new SuccessDataResult<Invoice>(invoice, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}
	
	private int calculateDayBetweenDates(LocalDate from, LocalDate to) {
		Long totalDay= ChronoUnit.DAYS.between(from, to);
		return totalDay.intValue();
	}
	
	private void rentalDateSetter(Invoice invoice) {
		Rent rent = this.rentService.getByIdAsEntityRent(invoice.getRent().getRentId());
		invoice.setFromDate(rent.getStartDate());
		invoice.setToDate(rent.getFinishDate());
		Long totalDay= ChronoUnit.DAYS.between(rent.getStartDate(), rent.getFinishDate());
		invoice.setRentalDay(totalDay.intValue());
		
	}
	
	
	
	private int serviceAmount(Rent rent) {
		
		int serviceBill = 0;
		OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceService
				.getByIdAsEntity(rent.getOrderedAdditionalService().getOrderedAdditionalServiceId());
		for (AdditionalService additionalServices : orderedAdditionalService.getAdditionalServices()) {
			serviceBill += additionalServices.getAdditionalServiceDailyPrice();
		}
		
		return (serviceBill * calculateDayBetweenDates(rent.getStartDate(), rent.getFinishDate()));
	}
	
	
	private int cityExtraAmount(Rent rent) {
		int cityExtra = 0;
		if (rent.getRentedCity().getCityId() != rent.getDeliveredCity().getCityId()) {
			cityExtra = 750;
		}
		return cityExtra;
		
	}
	
	private int carRentalAmount(Rent rent) {
		Car car = this.carService.getByIdAsEntityCar(rent.getCar().getCarId());
		int carRentalPrice = car.getDailyPrice()* calculateDayBetweenDates(rent.getStartDate(), rent.getFinishDate());
		return carRentalPrice;
	}
	
	private void invoiceNoGenerator(Invoice invoice) {
		String generatedNo = Integer.toString(invoice.getInvoiceId()) + "-" + Integer.toString(invoice.getCustomer().getUserId())
		+ (int)(Math.random() * 9999 + 1);
		invoice.setInvoiceNo(generatedNo);
	}
	
	
	private void invoiceManuelMapping(Invoice invoice) throws BusinessException {
		Rent rent = this.rentService.getByIdAsEntityRent(invoice.getRent().getRentId());
		rentalDateSetter(invoice);
		invoiceNoGenerator(invoice);
		invoice.setExtraAmount(serviceAmount(rent) + cityExtraAmount(rent));
		invoice.setTotalAmount(serviceAmount(rent) + carRentalAmount(rent) + cityExtraAmount(rent));
		
	}
	
	private void checkIfInvoiceExistsById(int id) throws BusinessException {
		if(!this.invoiceDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.InvoiceMessages.INVOICE_NOT_FOUND);
		}
	}
	
	private Customer getCustomerById(int customerId) throws BusinessException {
		return this.customerService.getByIdAsEntityCustomer(customerId);
	}


	
	private Invoice invoiceCreatorMapper(Rent rent) {
		Invoice invoice = new Invoice();
		invoice.setCustomer(rent.getCustomer());
		invoice.setRent(rent);
		invoice.setCreationDate(LocalDate.now());
		invoice.setFromDate(rent.getStartDate());
		invoice.setToDate(rent.getFinishDate());
		Long totalDay= ChronoUnit.DAYS.between(rent.getStartDate(), rent.getFinishDate());
		invoice.setRentalDay(totalDay.intValue());
		invoiceNoGenerator(invoice);
		return invoice;
		
	}

	
}

package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.api.models.CreatePaymentModel;
import com.turkcell.rentACar.api.models.CreateRentModel;
import com.turkcell.rentACar.api.models.RentEndModel;
import com.turkcell.rentACar.api.models.UpdateRentModel;
import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetRentDto;
import com.turkcell.rentACar.business.dtos.listdto.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.listdto.RentListDto;
import com.turkcell.rentACar.business.requests.create.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.create.CreateRentRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteRentRequest;
import com.turkcell.rentACar.business.requests.end.EndRentRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.RentException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentDao;
import com.turkcell.rentACar.entities.concrates.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentManager implements RentService {
	private final RentDao rentDao;
	private final ModelMapperService modelMapperService;
	private final CarMaintenanceService carMaintenanceService;
	private final OrderedAdditionalServiceService orderedAdditionalServiceService;
	private final IndividualCustomerService individualCustomerService;
	private final CorporateCustomerService corporateCustomerService;
	private final CustomerService customerService;
	private final CarService carService;
	private final PaymentService paymentService;
	private final InvoiceService invoiceService;
	private final PosService posService;
	private final CreditCardService creditCardService;

	@Autowired
	public RentManager(RentDao rentDao, ModelMapperService modelMapperService,
			@Lazy CarMaintenanceService carMaintenanceService,
			@Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,
			@Lazy IndividualCustomerService individualCustomerService,
			@Lazy CorporateCustomerService corporateCustomerService, @Lazy CustomerService customerService,
			CarService carService, @Lazy PaymentService paymentService, @Lazy InvoiceService invoiceService,
			PosService posService, CreditCardService creditCardService) {
		this.rentDao = rentDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.customerService = customerService;
		this.carService = carService;
		this.paymentService = paymentService;
		this.invoiceService = invoiceService;
		this.posService = posService;
		this.creditCardService = creditCardService;
	}

	@Override
	public DataResult<List<RentListDto>> getAll() {
		
		List<Rent> result = this.rentDao.findAll();
		
		List<RentListDto> response = result.stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetRentDto> getById(int id) throws RentException {
		
		checkIfRentExistsById(id);
		
		Rent foundRent = rentDao.getById(id);
		GetRentDto response = this.modelMapperService.forDto().map(foundRent, GetRentDto.class);
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentListDto>> getBy_CarId(int carId) throws BusinessException {

		checkIfCarExists(carId);
		List<Rent> result = this.rentDao.findByCar_CarId(carId);
		List<RentListDto> response = result.stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = BusinessException.class)
	@Override
	public DataResult<Rent> addRentForIndividualCustomer(CreateRentModel createRentModel) throws BusinessException {
		
		checkIfIndividualCustomerExists(createRentModel.getCreateRentRequest().getCustomerId());

		Rent rent = rentCorrector(createRentModel);

		return new SuccessDataResult<>(rent, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = BusinessException.class)
	@Override
	public DataResult<Rent> addRentForCorporateCustomer(CreateRentModel createRentModel) throws BusinessException {
		
		checkIfCorporateCustomerExists(createRentModel.getCreateRentRequest().getCustomerId());

		Rent rent = rentCorrector(createRentModel);

		return new SuccessDataResult<>(rent, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}


	@Override
	public Result delete(DeleteRentRequest deleteRentRequest) throws RentException {

		checkIfRentExistsById(deleteRentRequest.getRentId());

		this.rentDao.deleteById(deleteRentRequest.getRentId());

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateRentModel updateRentModel) throws BusinessException {
		
		checkIfDatesCorrect(updateRentModel.getUpdateRentRequest().getStartDate(), updateRentModel.getUpdateRentRequest().getFinishDate());
		checkIfRentExistsById(updateRentModel.getUpdateRentRequest().getRentId());
		checkIfCarIsInMaintenance(updateRentModel.getUpdateRentRequest().getCarId());

		Rent rent = this.modelMapperService.forRequest().map(updateRentModel.getUpdateRentRequest(), Rent.class);
		checkIfCarIsInRent(rent);

		this.rentDao.save(rent);
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public Result endRent(RentEndModel rentEndModel) throws BusinessException {
		
		checkIfRentExistsById(rentEndModel.getEndRentRequest().getRentId());
		checkEndingKilometerIsTrue(rentEndModel.getEndRentRequest());
		Rent rent = this.rentDao.getById(rentEndModel.getEndRentRequest().getRentId());

		checkIfDatesCorrect(rent.getStartDate(), rentEndModel.getEndRentRequest().getFinishDate());
		rent.setEndingKilometer(rentEndModel.getEndRentRequest().getEndingKilometer());
		carService.updateRentedCarKilometer(rent.getCar().getCarId(), rent.getEndingKilometer());

		this.rentDao.save(rent);
		
		ifRentIsOutOfDatePay(rentEndModel, rent);

		return new SuccessResult(BusinessMessages.RentMessages.CAR_RENTAL_ENDED_WITH_PAYMENT_SUCCESSFULLY);
	}

	@Override
	public Rent getByIdAsEntityRent(int rentId) {
		return this.rentDao.getById(rentId);
	}

	private Rent rentCorrector(CreateRentModel createRentModel) throws BusinessException {
		checkIfDatesCorrect(createRentModel.getCreateRentRequest().getStartDate(), createRentModel.getCreateRentRequest().getFinishDate());
		checkIfCarExists(createRentModel.getCreateRentRequest().getCarId());
		checkIfCarIsInMaintenance(createRentModel.getCreateRentRequest().getCarId());

		Rent rent = this.modelMapperService.forRequest().map(createRentModel.getCreateRentRequest(), Rent.class);

		checkIfCarIsInRent(rent);
		rentMapper(rent, createRentModel);

		this.rentDao.save(rent);
		return  rent;
	}

	private void checkIfRentExistsById(int id) throws RentException {
		if (!this.rentDao.existsById(id)) {
			throw new RentException(BusinessMessages.RentMessages.RENTAL_CAR_NOT_FOUND);
		}
	}

	// check it
	private void checkIfCarIsInMaintenance(int carId) throws BusinessException {
		DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(carId);
		List<CarMaintenance> response = result.getData().stream()
				.map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class))
				.collect(Collectors.toList());
		for (CarMaintenance carMaintenances : response) {
			if (carMaintenances.getReturnDate() == null) {
				throw new BusinessException(BusinessMessages.RentMessages.CAR_IS_AT_MAINTENANCE);
			}
		}
	}

	private void rentMapper(Rent rent, CreateRentModel createRentModel) throws BusinessException {
		CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest = new CreateOrderedAdditionalServiceRequest();
		for (int i = 0; i < createRentModel.getCreateRentRequest().getAdditionalServices().size(); i++) {
			createOrderedAdditionalServiceRequest
					.setAdditionalServices(createRentModel.getCreateRentRequest().getAdditionalServices());
		}
		rent.setOrderedAdditionalService(
				this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest).getData());

		rent.setCustomer(getCustomerById(createRentModel.getCreateRentRequest().getCustomerId()));
	}

	private void ifRentIsOutOfDatePay(RentEndModel rentEndModel, Rent rent) throws BusinessException {
		
		if (rentEndModel.getEndRentRequest().getFinishDate().isAfter(rent.getFinishDate())) {

			CreateRentModel createRentModel = new CreateRentModel();
			createRentModel.setCreateRentRequest(mappingForCreateRent(rent));
			createRentModel.getCreateRentRequest().setFinishDate(rentEndModel.getEndRentRequest().getFinishDate());

			CreatePaymentModel createPaymentModel = new CreatePaymentModel();
			createPaymentModel.setCreateRentModel(createRentModel);
			createPaymentModel.setCreateCreditCardRequest(rentEndModel.getCreateCreditCardRequest());

			if (individualCustomerService
					.individualCustomerExistsById(createRentModel.getCreateRentRequest().getCustomerId()).isSuccess()) {
				Rent rentIndividual = this.modelMapperService.forRequest().map(createRentModel.getCreateRentRequest(),
						Rent.class);
				additionalPaymentCreator(rentIndividual, createRentModel, rentEndModel);

			} else {
				Rent rentCorporate = this.modelMapperService.forRequest().map(createRentModel.getCreateRentRequest(),
						Rent.class);
				additionalPaymentCreator(rentCorporate, createRentModel, rentEndModel);
			}

		}
	}
	
	private void checkIfDatesCorrect(LocalDate from, LocalDate to) throws RentException {
		if(from.isAfter(to)) {
			throw new RentException(BusinessMessages.RentMessages.RENTAL_DATES_ARE_NOT_CORRECT);
		}
	}

	private void additionalPaymentCreator(Rent rent, CreateRentModel createRentModel, RentEndModel rentEndModel)
			throws BusinessException {
		rentMapper(rent, createRentModel);

		rent.setEndingKilometer(rentEndModel.getEndRentRequest().getEndingKilometer());
		carService.updateRentedCarKilometer(rent.getCar().getCarId(), rent.getEndingKilometer());

		this.rentDao.save(rent);

		Invoice invoice = this.invoiceService.invoiceCreatorForEndRent(rent).getData();

		this.posService.pos(rentEndModel.getCreateCreditCardRequest());

		Payment payment = paymentMapper(invoice, rent);
		this.paymentService.paymentCreator(payment);
	}

	private Payment paymentMapper(Invoice invoice, Rent rent) {
		Payment payment = new Payment();
		payment.setInvoice(invoice);
		payment.setRent(rent);
		payment.setCustomer(rent.getCustomer());
		payment.setPaymentAmount(invoice.getTotalAmount());
		return payment;
	}

	private CreateRentRequest mappingForCreateRent(Rent rent) {
		CreateRentRequest createRentRequest = new CreateRentRequest();
		createRentRequest.setCustomerId(rent.getCustomer().getUserId());
		createRentRequest.setCarId(rent.getCar().getCarId());
		createRentRequest.setRentedCityId(rent.getRentedCity().getCityId());
		createRentRequest.setDeliveredCityId(rent.getDeliveredCity().getCityId());
		createRentRequest.setStartDate(rent.getFinishDate()); 
																

		List<Integer> additionalServiceIds = new ArrayList<>();
		for (int i = 0; i < rent.getOrderedAdditionalService().getAdditionalServices().size(); i++) {
			additionalServiceIds
					.add(rent.getOrderedAdditionalService().getAdditionalServices().get(i).getAdditionalServiceId());
		}

		createRentRequest.setAdditionalServices(additionalServiceIds);

		return createRentRequest;
	}

	private void checkIfCarIsInRent(Rent rent) throws BusinessException {
		List<Rent> rentList = this.rentDao.findByCar_CarId(rent.getCar().getCarId());

		for (Rent rents : rentList) {
			while (rent.getRentId() != rents.getRentId()) {
				if (rent.getStartDate().isBefore(rents.getFinishDate())
						&& rent.getStartDate().isAfter(rents.getStartDate())) {
					throw new BusinessException(BusinessMessages.RentMessages.CAR_IS_ALREADY_RENTED);
				}
			}
		}

	}

	private void checkIfCarExists(int id) throws BusinessException {
		this.carService.isCarExistsById(id);
	}

	private void checkIfIndividualCustomerExists(int id) throws BusinessException {
		if (!individualCustomerService.individualCustomerExistsById(id).isSuccess()) {
			throw new BusinessException(BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND);
		}
	}

	private void checkIfCorporateCustomerExists(int id) throws BusinessException {
		if (!corporateCustomerService.corporateCustomerExistsById(id).isSuccess()) {
			throw new BusinessException(BusinessMessages.CorporateCustomerMessages.CORPORATE_CUSTOMER_NOT_FOUND);
		}
	}

	private void checkEndingKilometerIsTrue(EndRentRequest endRentRequest) throws BusinessException {
		Rent rent = this.rentDao.getById(endRentRequest.getRentId());
		Car car = this.carService.getByIdAsEntityCar(rent.getCar().getCarId());
		if (endRentRequest.getEndingKilometer() <= car.getCurrentKilometer()) {
			throw new BusinessException(BusinessMessages.RentMessages.INVALID_KM);
		}

	}

	private Customer getCustomerById(int customerId) throws BusinessException {
		return this.customerService.getByIdAsEntityCustomer(customerId);
	}

}

package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.api.models.CardSaverEnum;
import com.turkcell.rentACar.api.models.CreatePaymentModel;
import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetCarDto;
import com.turkcell.rentACar.business.dtos.getdto.GetPaymentDto;
import com.turkcell.rentACar.business.dtos.listdto.PaymentListDto;
import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.delete.DeletePaymentRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.PaymentException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concrates.Car;
import com.turkcell.rentACar.entities.concrates.Invoice;
import com.turkcell.rentACar.entities.concrates.Payment;
import com.turkcell.rentACar.entities.concrates.Rent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentManager implements PaymentService {

	private final PaymentDao paymentDao;
	private final ModelMapperService modelMapperService;
	private final PosService posService;
	private final CreditCardService creditCardService;
	private final RentService rentService;
	private final InvoiceService invoiceService;


	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, PosService posService,
						  CreditCardService creditCardService, RentService rentService, InvoiceService invoiceService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.posService = posService;
		this.creditCardService = creditCardService;
		this.rentService = rentService;
		this.invoiceService = invoiceService;
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {
		
		List<Payment> result = this.paymentDao.findAll();
		
		List<PaymentListDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
				.collect(Collectors.toList());

		for (int i = 0; i < result.size(); i++) {
			response.get(i).setCustomerId(result.get(i).getCustomer().getUserId());
			response.get(i).setInvoiceId(result.get(i).getInvoice().getInvoiceId());
		}

		return new SuccessDataResult<>(response,
				BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetPaymentDto> getById(int paymentId) throws PaymentException {
		
		checkIfPaymentExistsById(paymentId);
		
		Payment payment = this.paymentDao.getById(paymentId);
		GetPaymentDto paymentDto = this.modelMapperService.forDto().map(payment, GetPaymentDto.class);
		
		paymentDto.setCustomerId(payment.getCustomer().getUserId());
		paymentDto.setInvoiceId(payment.getInvoice().getInvoiceId());

		return new SuccessDataResult<>(paymentDto,
				BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public Result addPaymentForIndividualCustomer(CreatePaymentModel createPaymentModel, CardSaverEnum cardSaverEnum)
			throws BusinessException {
		
		Rent rent = this.rentService.addRentForIndividualCustomer(createPaymentModel.getCreateRentModel()).getData();

		addRentWithPaymentAndInvoice(rent, createPaymentModel, cardSaverEnum);

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public Result addPaymentForCorporateCustomer(CreatePaymentModel createPaymentModel, CardSaverEnum cardSaverEnum)
			throws BusinessException {
		
		Rent rent = this.rentService.addRentForCorporateCustomer(createPaymentModel.getCreateRentModel()).getData();

		addRentWithPaymentAndInvoice(rent, createPaymentModel, cardSaverEnum);

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}


	private void addRentWithPaymentAndInvoice(Rent rent, CreatePaymentModel createPaymentModel, CardSaverEnum cardSaverEnum) throws BusinessException {

		checkIfCarIsInRentInPayment(rent.getCar().getCarId());

		Invoice invoice = this.invoiceService.invoiceCreator(rent).getData();

		saveCreditCard(createPaymentModel.getCreateCreditCardRequest(), cardSaverEnum, rent.getCustomer().getUserId());

		this.posService.pos(createPaymentModel.getCreateCreditCardRequest());

		Payment payment = paymentMapper(invoice, rent);

		this.paymentDao.save(payment);

	}

	@Override
	public Result delete(DeletePaymentRequest deletePaymentRequest) throws PaymentException {
		
		checkIfPaymentExistsById(deletePaymentRequest.getPaymentId());
		
		this.paymentDao.deleteById(deletePaymentRequest.getPaymentId());
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}
	
	@Override
	public Result paymentCreator(Payment payment) {
		
		this.paymentDao.save(payment);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	private Payment paymentMapper(Invoice invoice, Rent rent) {
		Payment payment = new Payment();
		payment.setInvoice(invoice);
		payment.setRent(rent);
		payment.setCustomer(rent.getCustomer());
		payment.setPaymentAmount(invoice.getTotalAmount());
		return payment;
	}

	private void saveCreditCard(CreateCreditCardRequest createCreditCardRequest, CardSaverEnum cardSaverEnum,
			int customerId) throws BusinessException {
		if (cardSaverEnum == CardSaverEnum.YES) {
			this.creditCardService.add(createCreditCardRequest, customerId);
		}
	}
	
	private void checkIfPaymentExistsById(int id) throws PaymentException {
		if(!this.paymentDao.existsById(id)) {
			throw new PaymentException(BusinessMessages.PaymentMessages.PAYMENT_NOT_FOUND);
		}
	}

	private void checkIfCarIsInRentInPayment(int carId) throws BusinessException {
		if (!this.rentService.getBy_CarId(carId).isSuccess()){
			throw  new BusinessException(BusinessMessages.RentMessages.CAR_IS_ALREADY_RENTED);
		}
	}
	

}

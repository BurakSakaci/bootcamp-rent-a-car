package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.api.models.CardSaverEnum;
import com.turkcell.rentACar.api.models.CreatePaymentModel;
import com.turkcell.rentACar.business.dtos.getdto.GetPaymentDto;
import com.turkcell.rentACar.business.dtos.listdto.PaymentListDto;
import com.turkcell.rentACar.business.requests.delete.DeletePaymentRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.PaymentException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concrates.Payment;
import com.turkcell.rentACar.entities.concrates.Rent;

public interface PaymentService {

	DataResult<List<PaymentListDto>> getAll();
	DataResult<GetPaymentDto> getById(int paymentId) throws PaymentException;
	Result addPaymentForIndividualCustomer(CreatePaymentModel createPaymentModel, CardSaverEnum cardSaverEnum) throws BusinessException;
	Result addPaymentForCorporateCustomer(CreatePaymentModel createPaymentModel, CardSaverEnum cardSaverEnum) throws BusinessException;
	Result delete(DeletePaymentRequest deletePaymentRequest) throws PaymentException;
	Result paymentCreator(Payment payment);
		
}

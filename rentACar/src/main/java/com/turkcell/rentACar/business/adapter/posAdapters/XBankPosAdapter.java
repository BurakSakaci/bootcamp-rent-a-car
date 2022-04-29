package com.turkcell.rentACar.business.adapter.posAdapters;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.outServices.XBankPosManager;
import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.PaymentException;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;

@Primary
@Service
public class XBankPosAdapter implements PosService{

	@Override
	public Result pos(CreateCreditCardRequest createCreditCardRequest) throws PaymentException {
		XBankPosManager xBankPosManager = new XBankPosManager();
		
		boolean paymentResult = xBankPosManager.makePayment(createCreditCardRequest);
		
		checkIfPaymentSuccess(paymentResult);
		
		if (paymentResult) {
			return new SuccessResult("Ödeme tamamlandı.");
		}
		return new ErrorResult("Ödeme başarısız.");
	}

	
	private void checkIfPaymentSuccess(boolean result) throws PaymentException {
		if(!result) {
			throw new PaymentException(BusinessMessages.PaymentMessages.INSUFFICIENT_BALANCE);
		}
	}
	
	
}

package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.PaymentException;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface PosService {
	
	Result pos(CreateCreditCardRequest createCreditCardRequest) throws PaymentException;
}

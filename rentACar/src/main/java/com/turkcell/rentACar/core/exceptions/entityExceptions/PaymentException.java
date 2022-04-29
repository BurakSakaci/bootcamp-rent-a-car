package com.turkcell.rentACar.core.exceptions.entityExceptions;

import com.turkcell.rentACar.core.exceptions.BusinessException;

public class PaymentException extends BusinessException{
	public PaymentException(String message) {
		super(message);
	}
}

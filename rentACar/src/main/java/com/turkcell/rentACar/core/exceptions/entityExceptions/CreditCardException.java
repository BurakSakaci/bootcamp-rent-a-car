package com.turkcell.rentACar.core.exceptions.entityExceptions;

import com.turkcell.rentACar.core.exceptions.BusinessException;

public class CreditCardException extends BusinessException{
	public CreditCardException(String message) {
		super(message);
	}
}

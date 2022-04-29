package com.turkcell.rentACar.core.exceptions.entityExceptions;

import com.turkcell.rentACar.core.exceptions.BusinessException;

public class CorporateCustomerException extends BusinessException{
	public CorporateCustomerException(String message) {
		super(message);
	}
}

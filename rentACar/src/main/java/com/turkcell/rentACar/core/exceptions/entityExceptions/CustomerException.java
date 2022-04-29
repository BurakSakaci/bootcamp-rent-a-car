package com.turkcell.rentACar.core.exceptions.entityExceptions;

import com.turkcell.rentACar.core.exceptions.BusinessException;

public class CustomerException extends BusinessException{
	public CustomerException(String message) {
		super(message);
	}
}

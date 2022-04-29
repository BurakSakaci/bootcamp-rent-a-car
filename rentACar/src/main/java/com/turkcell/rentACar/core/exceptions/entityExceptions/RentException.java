package com.turkcell.rentACar.core.exceptions.entityExceptions;

import com.turkcell.rentACar.core.exceptions.BusinessException;

public class RentException extends BusinessException{
	public RentException(String message) {
		super(message);
	}
}

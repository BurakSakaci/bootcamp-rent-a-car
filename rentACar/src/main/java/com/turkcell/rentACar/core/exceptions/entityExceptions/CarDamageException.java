package com.turkcell.rentACar.core.exceptions.entityExceptions;

import com.turkcell.rentACar.core.exceptions.BusinessException;

public class CarDamageException extends BusinessException{
	public CarDamageException(String message) {
		super(message);
	}
}

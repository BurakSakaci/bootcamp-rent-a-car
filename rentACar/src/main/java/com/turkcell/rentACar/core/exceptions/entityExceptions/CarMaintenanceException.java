package com.turkcell.rentACar.core.exceptions.entityExceptions;

import com.turkcell.rentACar.core.exceptions.BusinessException;

public class CarMaintenanceException extends BusinessException{
	public CarMaintenanceException(String message) {
		super(message);
	}
}

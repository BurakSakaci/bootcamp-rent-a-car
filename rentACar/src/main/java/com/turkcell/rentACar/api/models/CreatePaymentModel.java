package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.create.CreateRentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentModel {

	private CreateCreditCardRequest createCreditCardRequest;
	
	private CreateRentModel createRentModel;
}

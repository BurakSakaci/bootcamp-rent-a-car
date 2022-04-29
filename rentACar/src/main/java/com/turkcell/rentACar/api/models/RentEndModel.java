package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.end.EndRentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentEndModel {

	private CreateCreditCardRequest createCreditCardRequest;
	private EndRentRequest endRentRequest;
}

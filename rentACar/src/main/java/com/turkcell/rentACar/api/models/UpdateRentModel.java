package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.update.UpdateRentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentModel {
	private UpdateRentRequest updateRentRequest;
}

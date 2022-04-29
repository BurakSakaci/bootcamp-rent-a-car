package com.turkcell.rentACar.business.requests.create;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarDamageRequest {
	
	@Size(min = 2)
	private String damageDescription;
	
	@NotNull
	@Positive
	private int carId;

}

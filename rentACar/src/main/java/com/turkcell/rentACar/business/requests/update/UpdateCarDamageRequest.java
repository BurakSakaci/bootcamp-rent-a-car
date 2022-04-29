package com.turkcell.rentACar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarDamageRequest {
	
	@NotNull
	@Positive
	private int carDamageId;
	
	@Size(min = 2)
	private String damageDescription;
	
	@NotNull
	@Positive
	private int carId;
	
	
}

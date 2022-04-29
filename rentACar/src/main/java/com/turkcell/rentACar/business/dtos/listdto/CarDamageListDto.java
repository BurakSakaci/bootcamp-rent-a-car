package com.turkcell.rentACar.business.dtos.listdto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageListDto {

	private int carDamageId;
	
	private String damageDescription;
	
	private int carId;
	
}

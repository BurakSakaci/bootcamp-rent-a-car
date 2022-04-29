package com.turkcell.rentACar.business.dtos.getdto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarDamageDto {

	private int carDamageId;

	private String damageDescription;

	private int carId;
}

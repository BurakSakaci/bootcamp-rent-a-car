package com.turkcell.rentACar.business.dtos.getdto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCarDto {

	private int id;

	private int dailyPrice;

	private int modelYear;

	private String description;
	
	private int currentKilometer;

	private String brandName;

	private String colorName;

}

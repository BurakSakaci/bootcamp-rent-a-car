package com.turkcell.rentACar.business.dtos.getdto;

import java.time.LocalDate;

import com.turkcell.rentACar.entities.concrates.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRentDto {

	private int rentId;

	private int rentedCityId;

	private int deliveredCityId;

	private int startingKilometer;
	
	private int endingKilometer;
	
	private LocalDate startDate;

	private LocalDate finishDate;

	private int carId;

	private int orderedAdditionalServiceId;
		
	private int userId;

}

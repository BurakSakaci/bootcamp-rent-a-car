package com.turkcell.rentACar.business.requests.update;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.turkcell.rentACar.entities.concrates.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRentRequest {
	
	@NotNull
	@Positive
	private int rentId;
	
	@NotNull
	@Positive
	private int rentedCityId;
	
	
	@NotNull
	@Positive
	private int deliveredCityId;
	
	
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate finishDate;
	
	@NotNull
	@Positive
	private int carId;

	@NotNull
	@Positive
	private int orderedAdditionalServiceId;
	
	@NotNull
	@Positive
    private int customerId;

}

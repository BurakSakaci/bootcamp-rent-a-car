package com.turkcell.rentACar.business.requests.update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	@NotNull
	private int carId;

	@Min(100)
	@NotNull
	private int dailyPrice;
	@Min(2000)
	@NotNull
	private int modelYear;

	@NotNull
	@Size(min = 5)
	private String description;

	@NotNull
	private int currentKilometer;

	@NotNull
	private int brandId;

	@NotNull
	private int colorId;
}

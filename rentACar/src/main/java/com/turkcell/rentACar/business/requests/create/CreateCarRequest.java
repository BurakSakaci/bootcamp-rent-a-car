package com.turkcell.rentACar.business.requests.create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCarRequest {
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

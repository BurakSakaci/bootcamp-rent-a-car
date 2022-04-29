package com.turkcell.rentACar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.rentACar.business.requests.delete.DeleteCityRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityRequest {
	
	@NotNull
	@Positive
	private int cityId;
	
	@NotNull
	private String cityName;

}

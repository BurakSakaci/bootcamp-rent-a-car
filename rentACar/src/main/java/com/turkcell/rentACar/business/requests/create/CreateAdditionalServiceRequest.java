package com.turkcell.rentACar.business.requests.create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {

	@NotNull
	@NotBlank
	private String additionalServiceName;
	
	@NotNull
	@Positive
	private int additionalServiceDailyPrice;
	
}

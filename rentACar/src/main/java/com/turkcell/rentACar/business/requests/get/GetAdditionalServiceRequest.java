package com.turkcell.rentACar.business.requests.get;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAdditionalServiceRequest {

	@NotNull
	@Positive
	private int additionalServiceId;

	@NotNull
	@NotBlank
	private String additionalServiceName;

	@NotNull
	@Positive
	private int additionalServiceDailyPrice;
}

package com.turkcell.rentACar.business.requests.delete;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAdditionalServiceRequest {
	
	@NotNull
	@Positive
	private int additionalServiceId;

}

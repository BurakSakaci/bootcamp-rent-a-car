package com.turkcell.rentACar.business.requests.update;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {

	
	@NotNull
	@Positive
	private int orderedAdditionalServiceId;
	
	private List<Integer> additionalServices;
}

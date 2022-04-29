package com.turkcell.rentACar.business.requests.get;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderedAdditionalServiceRequest {
	
	@NotNull
	@Positive
	private int orderedAdditionalServiceId;
	
	private List<Integer> additionalServiceId;
	
	@NotNull
	@Positive
	private int rentId;

}

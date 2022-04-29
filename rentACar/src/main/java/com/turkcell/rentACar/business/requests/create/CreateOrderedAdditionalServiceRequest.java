package com.turkcell.rentACar.business.requests.create;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderedAdditionalServiceRequest {
	
	private List<Integer> additionalServices;

}

package com.turkcell.rentACar.business.requests.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarRequest {
	
	
	private String brandName;

	private String colorName;

}

package com.turkcell.rentACar.business.requests.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetColorRequest {
	private int colorId;
	private String colorName;
}

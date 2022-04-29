package com.turkcell.rentACar.business.requests.update;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateColorRequest {
	@NotNull
	private int colorId;
	@NotNull
	private String colorName;
}

package com.turkcell.rentACar.business.requests.delete;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCarRequest {
	@NotNull
	private int carId;
}

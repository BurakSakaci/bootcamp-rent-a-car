package com.turkcell.rentACar.business.requests.delete;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteCarMaintenanceRequest {
	@NotNull
	private int carMaintenanceId;

}

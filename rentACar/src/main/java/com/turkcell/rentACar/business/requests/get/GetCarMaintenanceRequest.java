package com.turkcell.rentACar.business.requests.get;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetCarMaintenanceRequest {
	@NotNull
	private String carMaintenanceDescription;
	@NotNull
	private LocalDate returnDate;
	@NotNull
	private int carId;
	
	
	
}

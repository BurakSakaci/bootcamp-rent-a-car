package com.turkcell.rentACar.business.requests.update;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCarMaintenanceRequest {
	@NotNull
	private int carMaintenanceId;
	@NotNull
	private String carMaintenanceDescription;
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate returnDate;
	@NotNull
	private int carId;
}

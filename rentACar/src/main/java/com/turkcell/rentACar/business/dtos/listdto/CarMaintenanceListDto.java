package com.turkcell.rentACar.business.dtos.listdto;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceListDto {
	private int carMaintenanceId;
	
	private String carMaintenanceDescription;

	private LocalDate returnDate;
	
	private int carId;
}

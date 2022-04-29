package com.turkcell.rentACar.business.dtos.getdto;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarMaintenanceDto {

	private String carMaintenanceDescription;

	private LocalDate returnDate;

	private int carId;
}

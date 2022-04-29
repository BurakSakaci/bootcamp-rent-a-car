package com.turkcell.rentACar.business.requests.delete;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.turkcell.rentACar.entities.concrates.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRentRequest {
	@NotNull
	private int rentId;

}

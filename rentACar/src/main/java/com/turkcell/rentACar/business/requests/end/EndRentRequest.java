package com.turkcell.rentACar.business.requests.end;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndRentRequest {
	
	@NotNull
	@Positive
	private int rentId;
	
	@NotNull
	@Positive
	private int endingKilometer;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate finishDate;

}

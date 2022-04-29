package com.turkcell.rentACar.business.requests.create;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

	private LocalDate creationDate;

	@NotNull
	@Positive
    private int customerId;

	@NotNull
	@Positive
	private int rentId;
	
}

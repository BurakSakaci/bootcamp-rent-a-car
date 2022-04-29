package com.turkcell.rentACar.business.requests.get;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInvoiceRequest {

	private int rentalDay;

	private LocalDate startDate;

	private LocalDate finishDate;

	private int bill;

	private int userId;

	private int rentId;
}

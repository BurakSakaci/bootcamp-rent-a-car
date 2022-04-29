package com.turkcell.rentACar.business.dtos.listdto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceListDto {

	private int invoiceId;	

	private String invoiceNo;	

	private LocalDate creationDate;	

	private LocalDate fromDate;		

	private LocalDate toDate;		

	private int rentalDay;			

	private int extraAmount;		

	private int totalAmount;		

	private int userId;

	private int rentId;

}

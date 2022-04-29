package com.turkcell.rentACar.business.dtos.listdto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentListDto {
	
	private int paymentId;
	
	private int totalAmount;
	
	private int rentId;
	
	private int invoiceId;
	
	private List<AdditionalServiceListDto> additionalServices;
	
	private int customerId;

}

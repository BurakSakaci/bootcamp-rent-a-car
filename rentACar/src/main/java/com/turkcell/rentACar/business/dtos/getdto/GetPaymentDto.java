package com.turkcell.rentACar.business.dtos.getdto;


import java.util.List;

import com.turkcell.rentACar.business.dtos.listdto.AdditionalServiceListDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentDto {
	private int paymentId;

	private int totalAmount;

	private int rentId;

	private int invoiceId;

	private List<AdditionalServiceListDto> additionalServices;
	
	private int customerId;
}

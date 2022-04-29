package com.turkcell.rentACar.business.dtos.listdto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardListDto {
	
	private String cardHolder;

	private String cardNo;

	private int expirationMonth;

	private int expirationYear;

	private int cvv;

	private int customerId;
}

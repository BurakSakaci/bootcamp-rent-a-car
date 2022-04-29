package com.turkcell.rentACar.business.dtos.getdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCreditCardDto {

	private String cardHolder;

	private String cardNo;

	private int expirationMonth;

	private int expirationYear;

	private int cvv;

	private int customerId;
}

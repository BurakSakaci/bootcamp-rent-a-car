package com.turkcell.rentACar.business.requests.create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.turkcell.rentACar.business.constants.messages.BusinessMessages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {
	
	@NotNull
	@NotBlank
	@Pattern(regexp = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$", message = BusinessMessages.CreditCardMessages.INVALID_CREDIT_CARD)
	private String cardNo;
	
	@NotNull
	@NotBlank
	private String cardHolder;
	
	@NotNull
	@NotBlank
	@Range(min = 1, max = 12, message = BusinessMessages.CreditCardMessages.INVALID_CARD_DATES)
	private int expirationMonth;
	
	@NotNull
	@NotBlank
	@Range(min = 2022, message = BusinessMessages.CreditCardMessages.INVALID_CARD_DATES)
	private int expirationYear;
	
	@NotNull
	@NotBlank
	@Pattern(regexp = "^[0-9]{3,4}$", message = BusinessMessages.CreditCardMessages.INVALID_CREDIT_CARD)
	private int cvv;
	
}

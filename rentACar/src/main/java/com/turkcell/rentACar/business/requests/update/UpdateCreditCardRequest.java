package com.turkcell.rentACar.business.requests.update;

import javax.validation.constraints.*;

import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {
	
	@NotNull
	@NotBlank
	private int cardId;
	
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
	@Pattern(regexp = "^[0-9]{3, 4}$", message = BusinessMessages.CreditCardMessages.INVALID_CREDIT_CARD)
	private int cvv;
	
	@NotNull
	@NotBlank
	private int customerId;
}

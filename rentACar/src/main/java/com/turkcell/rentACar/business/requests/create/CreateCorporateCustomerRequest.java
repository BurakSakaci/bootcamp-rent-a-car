package com.turkcell.rentACar.business.requests.create;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRequest {

	@NotNull
	@Email
	private String email;

	@NotNull
	private String password;
	
	@NotNull
	private String companyName;

	@NotNull
	@Size(min = 5, message = BusinessMessages.CorporateCustomerMessages.CORPORATE_CUSTOMER_INVALID_REQUEST)
	private String taxNumber;
}

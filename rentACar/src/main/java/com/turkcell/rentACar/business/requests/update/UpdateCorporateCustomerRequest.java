package com.turkcell.rentACar.business.requests.update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {

	private int customerId;

	@NotNull
	@Email
	private String email;

	@NotNull
	private String password;
	
	@NotNull
	private String companyName;

	@NotNull
	@Pattern(regexp = "/^[0-9]{10}$/\n", message = BusinessMessages.CorporateCustomerMessages.CORPORATE_CUSTOMER_INVALID_REQUEST)
	private String taxNumber;	
	
}

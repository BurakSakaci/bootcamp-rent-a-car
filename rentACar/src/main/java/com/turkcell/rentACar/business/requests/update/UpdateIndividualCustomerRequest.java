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
public class UpdateIndividualCustomerRequest {

	@NotNull
	private int customerId;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	@Pattern(regexp = "^[1-9]{1}[0-9]{9}[02468]{1}$\n", message = BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_INVALID_REQUEST)
	private String nationalIdentity;
}

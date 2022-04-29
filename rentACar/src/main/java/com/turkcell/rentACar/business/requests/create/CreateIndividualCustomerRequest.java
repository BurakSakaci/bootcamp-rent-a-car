package com.turkcell.rentACar.business.requests.create;

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
public class CreateIndividualCustomerRequest {
		
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
	@Pattern(regexp = "^[1-9]{1}[0-9]{10}", message = BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_INVALID_REQUEST)
	private String nationalIdentity;

}

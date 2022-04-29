package com.turkcell.rentACar.business.requests.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetIndividualCustomerRequest {
	private String email;

	private String password;

	private String firstName;

	private String lastName;

	private String nationalIdentity;
}

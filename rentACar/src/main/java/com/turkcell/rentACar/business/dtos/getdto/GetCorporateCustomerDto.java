package com.turkcell.rentACar.business.dtos.getdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCorporateCustomerDto {
	
    private int customerId;

	private String email;

	private String password;
	
	private String companyName;

	private String taxNumber;
}

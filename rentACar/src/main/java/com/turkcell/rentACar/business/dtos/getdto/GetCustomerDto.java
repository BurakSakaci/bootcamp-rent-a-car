package com.turkcell.rentACar.business.dtos.getdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerDto {

	private int customerId;

    private String email;
}

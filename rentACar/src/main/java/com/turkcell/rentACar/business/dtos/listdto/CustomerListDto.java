package com.turkcell.rentACar.business.dtos.listdto;

import com.turkcell.rentACar.business.dtos.getdto.GetCustomerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerListDto {
	private int customerId;

    private String email;
}

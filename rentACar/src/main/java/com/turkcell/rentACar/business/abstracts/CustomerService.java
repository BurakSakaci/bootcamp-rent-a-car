package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetCustomerDto;
import com.turkcell.rentACar.business.dtos.listdto.CustomerListDto;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.concrates.Customer;

public interface CustomerService {
	DataResult<List<CustomerListDto>> getAll();
    DataResult<GetCustomerDto> getById(int customerId) throws BusinessException;
    Customer getByIdAsEntityCustomer(int customerId) throws BusinessException;
    
}

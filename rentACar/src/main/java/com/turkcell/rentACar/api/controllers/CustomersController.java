package com.turkcell.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.dtos.getdto.GetCustomerDto;
import com.turkcell.rentACar.business.dtos.listdto.CustomerListDto;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {
	private CustomerService customerService;
	
	@Autowired
	public CustomersController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/getall")
	public DataResult<List<CustomerListDto>> getAll(){
		return this.customerService.getAll();
	}
	@GetMapping("/getById/{customerId}")
    public DataResult<GetCustomerDto> getById(@RequestParam int customerId) throws BusinessException{
    	return this.customerService.getById(customerId);
    }

}

package com.turkcell.rentACar.business.concrates;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.abstracts.RentService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetCustomerDto;
import com.turkcell.rentACar.business.dtos.listdto.CustomerListDto;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACar.entities.concrates.Customer;

@Service
public class CustomerManager implements CustomerService{
	
	private final CustomerDao customerDao;
	private final ModelMapperService modelMapperService;
	//rentservice maybe

	@Autowired
	public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService, @Lazy RentService rentService) {
		this.customerDao = customerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CustomerListDto>> getAll() {
		 List<Customer> result = this.customerDao.findAll();
	    
		 List<CustomerListDto> response = result.stream()
	                .map(customer -> this.modelMapperService.forDto().map(customer, CustomerListDto.class))
	                .collect(Collectors.toList());

	        return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCustomerDto> getById(int customerId) throws BusinessException {
		
		checkIfCustomerExistsById(customerId);
		Customer customer = this.customerDao.getById(customerId);
        GetCustomerDto customerDto = this.modelMapperService.forDto().map(customer, GetCustomerDto.class);

        return new SuccessDataResult<>(customerDto, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public Customer getByIdAsEntityCustomer(int customerId) throws BusinessException {
		
		checkIfCustomerExistsById(customerId);
		
		return this.customerDao.getById(customerId);
	}
	
	 private void checkIfCustomerExistsById(int customerId) throws BusinessException {
	        if(!this.customerDao.existsById(customerId)){
	            throw new BusinessException(BusinessMessages.CustomerMessages.CUSTOMER_NOT_FOUND);
	        }
	    }
	
	
	
}

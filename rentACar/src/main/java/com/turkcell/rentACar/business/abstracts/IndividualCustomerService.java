package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetIndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.listdto.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.IndividualCustomerException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface IndividualCustomerService {

	DataResult<List<IndividualCustomerListDto>> getAll();
	DataResult<GetIndividualCustomerDto> getById(int id) throws IndividualCustomerException;
	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws IndividualCustomerException;
	Result delete(int id) throws IndividualCustomerException;
	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws IndividualCustomerException;	
	Result individualCustomerExistsById(int id);

}

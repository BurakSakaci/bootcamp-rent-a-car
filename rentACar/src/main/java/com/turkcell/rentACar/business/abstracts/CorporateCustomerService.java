package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetCorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.listdto.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CorporateCustomerException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CorporateCustomerService {
	
	DataResult<List<CorporateCustomerListDto>> getAll();
	DataResult<GetCorporateCustomerDto> getById(int id) throws CorporateCustomerException;
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws CorporateCustomerException;
	Result delete(int id) throws CorporateCustomerException;
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws CorporateCustomerException;	
	Result corporateCustomerExistsById(int id);
	
}

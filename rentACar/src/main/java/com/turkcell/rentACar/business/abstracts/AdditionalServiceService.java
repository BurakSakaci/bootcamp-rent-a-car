package com.turkcell.rentACar.business.abstracts;

import java.util.List;
import java.util.Set;

import com.turkcell.rentACar.business.dtos.getdto.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.listdto.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concrates.AdditionalService;

public interface AdditionalServiceService {

	DataResult<List<AdditionalServiceListDto>> getAll();
	DataResult<GetAdditionalServiceDto> getById(int id) throws BusinessException;
	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException;
	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException;
	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;	
	
}

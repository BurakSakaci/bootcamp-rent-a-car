package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetCarDamageDto;
import com.turkcell.rentACar.business.dtos.listdto.CarDamageListDto;
import com.turkcell.rentACar.business.requests.create.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CarDamageException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarDamageService {

	DataResult<List<CarDamageListDto>> getAll();
	DataResult<GetCarDamageDto> getById(int id) throws CarDamageException;
	Result add(CreateCarDamageRequest createCarDamageRequest) throws BusinessException;
	Result delete(int id) throws CarDamageException;
	Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException;
	
	
}

package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetCityDto;
import com.turkcell.rentACar.business.dtos.listdto.CityListDto;
import com.turkcell.rentACar.business.requests.create.CreateCityRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCityRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCityRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CityException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CityService {
	
	DataResult<List<CityListDto>> getAll();
	DataResult<GetCityDto> getById(int id) throws CityException;
	Result add(CreateCityRequest createCityRequest) throws CityException;
	Result delete(DeleteCityRequest deleteCityRequest) throws CityException;
	Result update(UpdateCityRequest updateCityRequest) throws CityException;

	Result isCityExistsById(int cityId) throws CityException;

}

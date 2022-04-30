package com.turkcell.rentACar.business.concrates;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CacheService;
import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetCityDto;
import com.turkcell.rentACar.business.dtos.listdto.CityListDto;
import com.turkcell.rentACar.business.requests.create.CreateCityRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCityRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCityRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CityException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CityDao;
import com.turkcell.rentACar.entities.concrates.City;

@Service
public class CityManager implements CityService{
	
	private final CityDao cityDao;
	private final ModelMapperService modelMapperService;
	private final CacheService cacheService;

	@Autowired
	public CityManager(CityDao cityDao, ModelMapperService modelMapperService, CacheService cacheService) {
		this.cityDao = cityDao;
		this.modelMapperService = modelMapperService;
		this.cacheService = cacheService;
	}

	@Cacheable(value = "cities")
	@Override
	public DataResult<List<CityListDto>> getAll() {
		
		List<City> result = this.cityDao.findAll();
		
		List<CityListDto> response = result.stream().map(city -> this.modelMapperService.forDto().map(city, CityListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Cacheable(value = "cities", key = "#id")
	@Override
	public DataResult<GetCityDto> getById(int id) throws CityException {
		
		checkIfCityExistsById(id);
		
		City foundCity = this.cityDao.getById(id);
		GetCityDto response = this.modelMapperService.forDto().map(foundCity, GetCityDto.class);
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@CachePut(value = "cities")
	@Override
	public Result add(CreateCityRequest createCityRequest) throws CityException {
		
		checkIfCityExistsByName(createCityRequest.getCityName());
		
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
		
		this.cityDao.save(city);
		
		evictAllcachesAtIntervals();
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@CacheEvict(value = "cities", allEntries = true)
	@Override
	public Result delete(DeleteCityRequest deleteCityRequest) throws CityException {
		
		checkIfCityExistsById(deleteCityRequest.getCityId());
		
		this.cityDao.deleteById(deleteCityRequest.getCityId());
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}
	
	@CachePut(value = "cities")
	@Override
	public Result update(UpdateCityRequest updateCityRequest) throws CityException {
		
		checkIfCityExistsById(updateCityRequest.getCityId());
		checkIfCityExistsByName(updateCityRequest.getCityName());
		
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		
		this.cityDao.save(city);
		
		evictAllcachesAtIntervals();
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result isCityExistsById(int cityId) throws CityException {
		checkIfCityExistsById(cityId);
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Scheduled(fixedRate = 2000)
	public void evictAllcachesAtIntervals() {
		this.cacheService.evictAllCaches();
	}
	
	private void checkIfCityExistsById(int cityId) throws CityException {
		if(!this.cityDao.existsById(cityId)) {
			throw new CityException(BusinessMessages.CityMessages.CITY_NOT_FOUND);
		}
	}
	
	private void checkIfCityExistsByName(String cityName) throws CityException {
		if(this.cityDao.existsByCityName(cityName)) {
			throw new CityException(BusinessMessages.CityMessages.CITY_IS_ALREADY_EXISTS);
		}
	}
	

}
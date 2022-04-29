package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetCarDto;
import com.turkcell.rentACar.business.dtos.listdto.CarListDto;
import com.turkcell.rentACar.business.requests.create.CreateCarRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CarException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concrates.Car;

public interface CarService {
	DataResult<List<CarListDto>> getAll();
	DataResult<GetCarDto> getById(int id) throws BusinessException;
	DataResult<List<GetCarDto>> getByBrand_Id(int brandId)throws BusinessException;
	DataResult<List<GetCarDto>> getByColor_Id(int colorId)throws BusinessException;
	DataResult<List<GetCarDto>> getByBrand_IdAndColor_Id(int brandId, int colorId)throws BusinessException;
	DataResult<List<GetCarDto>> getAllPaged(int pageNo, int pageSize) throws BusinessException;
	DataResult<List<GetCarDto>> getAllSorted(String orderOfSort) throws CarException;
	DataResult<List<GetCarDto>> findByDailyPriceLessThan(int requestedPrice);
	DataResult<List<GetCarDto>> findByDailyPriceGreaterThan(int requestedPrice);
	DataResult<List<GetCarDto>> findByDailyPriceBetween(int minValue, int maxValue) throws BusinessException;
	Result add(CreateCarRequest createCarRequest) throws BusinessException;
	Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;
	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;
	void isCarExistsById(int id) throws BusinessException;
	Result updateRentedCarKilometer(int id, int endingKilometer) ;
	Car getByIdAsEntityCar(int carId);
	
}

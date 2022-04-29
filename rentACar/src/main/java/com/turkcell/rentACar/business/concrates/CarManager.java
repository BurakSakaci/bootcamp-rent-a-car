package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.abstracts.CacheService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetCarDto;
import com.turkcell.rentACar.business.dtos.listdto.CarListDto;
import com.turkcell.rentACar.business.requests.create.CreateCarRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CarException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.entities.concrates.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarManager implements CarService {
	private final CarDao carDao;
	private final ModelMapperService modelMapperService;
	private final BrandService brandService;
	private final ColorService colorService;
	private final CacheService cacheService;


	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService, CacheService cacheService, BrandService brandService ,ColorService colorService) {
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.cacheService = cacheService;
		this.brandService = brandService;
		this.colorService = colorService;
	}

	@Cacheable(value = "cars")
	@Override
	public DataResult<List<CarListDto>> getAll() {
		List<Car> result = this.carDao.findAll();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Cacheable(value = "cars", key = "#id")
	@Override
	public DataResult<GetCarDto> getById(int id) throws CarException {
		checkIfCarExistsById(id);
		Car foundCar = carDao.getById(id);
		GetCarDto response = this.modelMapperService.forDto().map(foundCar, GetCarDto.class);
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override 
	public DataResult<List<GetCarDto>> getAllPaged(int pageNo, int pageSize) throws BusinessException {
		checkIfPageNumbersCorrect(pageNo, pageSize);
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<GetCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.PAGED_SUCCESSFULLY);
	}
	
	@Override
	public DataResult<List<GetCarDto>> getAllSorted(String orderOfSort) throws CarException {
		
		Sort sort = setSortingOrder(orderOfSort);
		
		List<Car> result = this.carDao.findAll(sort);
		List<GetCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.CarMessages.SORTED_CAR_LIST_SUCCESS);
	}
	
	private Sort setSortingOrder(String orderOfSort) throws CarException {
		Sort sort;
		if (orderOfSort.toUpperCase().equals("ASC")) {
			sort = Sort.by(Sort.Direction.ASC, "dailyPrice");
		} else if (orderOfSort.toUpperCase().equals("DESC")) {
			sort = Sort.by(Sort.Direction.DESC, "dailyPrice");
		} else {
			throw new CarException(BusinessMessages.CarMessages.INVALID_ORDER_OF_SORT);
		}
		return sort;
	}

	@Override
	public DataResult<List<GetCarDto>> findByDailyPriceLessThan(int requestedPrice) {
		
		List<Car> result = this.carDao.findByDailyPriceLessThan(requestedPrice);
		List<GetCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.CarMessages.SORTED_CAR_LIST_LESS_THAN);
	}

	@Override
	public DataResult<List<GetCarDto>> findByDailyPriceGreaterThan(int requestedPrice) {
		
		List<Car> result = this.carDao.findByDailyPriceGreaterThan(requestedPrice);
		List<GetCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.CarMessages.SORTED_CAR_LIST_GREATER_THAN);
	}

	@Override
	public DataResult<List<GetCarDto>> findByDailyPriceBetween(int minValue, int maxValue) throws BusinessException {
		
		checkIfPriceIsCorrect(minValue, maxValue);
		
		List<Car> result = this.carDao.findByDailyPriceBetween(minValue, maxValue);

		List<GetCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class))
					.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CarMessages.CAR_LIST_BETWEEN_PRICE);
	}
	
	private void checkIfPriceIsCorrect(int minValue, int maxValue) throws CarException {
		if(minValue > maxValue) {
			throw new CarException(BusinessMessages.CarMessages.INVALID_PRICE);
		}
	}

	@CachePut(value = "cars")
	@Override
	public Result add(CreateCarRequest createCarRequest) throws BusinessException {
		
		checkIfBrandExistsById(createCarRequest.getBrandId());
		checkIfColorExistsById(createCarRequest.getColorId());
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		
		this.carDao.save(car);
		
		evictAllcachesAtIntervals();

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@CacheEvict(value = "cars", allEntries = true)
	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws CarException {

		checkIfCarExistsById(deleteCarRequest.getCarId());
		
		this.carDao.deleteById(deleteCarRequest.getCarId());
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@CachePut(value = "cars")
	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
		
		checkIfCarExistsById(updateCarRequest.getCarId());
		checkIfBrandExistsById(updateCarRequest.getBrandId());
		checkIfColorExistsById(updateCarRequest.getColorId());
		
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		
		evictAllcachesAtIntervals();

		this.carDao.save(car);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<GetCarDto>> getByBrand_Id(int brandId) throws BusinessException{
		
		checkIfBrandExistsById(brandId);
		
		List<Car> result = this.carDao.findByBrand_BrandId(brandId);
		List<GetCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<GetCarDto>> getByColor_Id(int colorId) throws BusinessException {
		
		checkIfColorExistsById(colorId);
		
		List<Car> result = this.carDao.findByColor_ColorId(colorId);
		List<GetCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());
	
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<GetCarDto>> getByBrand_IdAndColor_Id(int brandId, int colorId) throws BusinessException {
		
		checkIfBrandExistsById(brandId);
		checkIfColorExistsById(colorId);
			
		List<Car> result = this.carDao.findByBrand_BrandIdAndColor_ColorId(brandId, colorId);
		List<GetCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());

			return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}
	
	@Scheduled(fixedRate = 2000)
	public void evictAllcachesAtIntervals() {
		this.cacheService.evictAllCaches();
	}
	
	private void checkIfCarExistsById(int id) throws CarException {
		if (!this.carDao.existsById(id)) {
			throw new CarException(BusinessMessages.CarMessages.CAR_NOT_FOUND);
		}
	}
	
	private void checkIfBrandExistsById(int brandId) throws BusinessException {
		if(!this.brandService.getById(brandId).isSuccess()) {
			throw new CarException(BusinessMessages.CarMessages.BRAND_NOT_FOUND_IN_CARS);
		}
	}
	
	private void checkIfColorExistsById(int colorId) throws BusinessException {
		if(!this.colorService.getById(colorId).isSuccess()) {
			throw new CarException(BusinessMessages.CarMessages.COLOR_NOT_FOUND_IN_CARS);
		}
	}
	
	private void checkIfPageNumbersCorrect(int pageNo, int pageSize) throws BusinessException {
		if(pageNo<1 || pageSize<1) {
			throw new BusinessException(BusinessMessages.CarMessages.PAGE_NUMBERS_INCORRECT);
		}
	}

	//checkout
	@Override
	public void isCarExistsById(int id) throws BusinessException {
		if(!carDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CarMessages.CAR_NOT_FOUND);
		}
	}

	@Override
	public Car getByIdAsEntityCar(int carId) {
		return this.carDao.getById(carId);
	}

	@Override
	public Result updateRentedCarKilometer(int id, int endingKilometer) {
		
		Car car = carDao.getById(id);
		
		car.setCurrentKilometer(endingKilometer);
		
		carDao.save(car);
		return new SuccessResult(BusinessMessages.CarMessages.CAR_KM_UPDATED);
	}


}

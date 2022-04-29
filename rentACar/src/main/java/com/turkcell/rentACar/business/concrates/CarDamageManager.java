package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetCarDamageDto;
import com.turkcell.rentACar.business.dtos.listdto.CarDamageListDto;
import com.turkcell.rentACar.business.requests.create.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CarDamageException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentACar.entities.concrates.CarDamage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarDamageManager implements CarDamageService{
	
	private final CarDamageDao carDamageDao;
	private final ModelMapperService modelMapperService;

	private final CarService carService;

	@Autowired
	public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService, CarService carService) {
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}

	@Override
	public DataResult<List<CarDamageListDto>> getAll() {
		
		List<CarDamage> result = this.carDamageDao.findAll();
		
		List<CarDamageListDto> response = result.stream()
				.map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
				.collect(Collectors.toList());
		mappingDamage(response, result);
		
		return new SuccessDataResult<List<CarDamageListDto>>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCarDamageDto> getById(int id) throws CarDamageException {
		
		checkIFCarDamageExistsById(id);
		
		CarDamage carDamage = this.carDamageDao.getById(id);
		GetCarDamageDto response = this.modelMapperService.forDto().map(carDamage, GetCarDamageDto.class);
		response.setCarId(carDamage.getCar().getCarId());
		
		return new SuccessDataResult<GetCarDamageDto>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) throws BusinessException {

		checkIfCarExistsById(createCarDamageRequest.getCarId());
		CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);
		carDamageDao.save(carDamage);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) throws CarDamageException {
		checkIFCarDamageExistsById(id);
		this.carDamageDao.deleteById(id);
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {

		checkIfCarExistsById(updateCarDamageRequest.getCarId());
		checkIFCarDamageExistsById(updateCarDamageRequest.getCarDamageId());
		
		CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
		
		carDamageDao.save(carDamage);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}
	
	private void checkIFCarDamageExistsById(int carDamageId) throws CarDamageException {
		if(!this.carDamageDao.existsById(carDamageId)) {
			throw new CarDamageException(BusinessMessages.CarDamageMessages.CAR_DAMAGE_NOT_FOUND);
		}
	}
	
	private void mappingDamage(List<CarDamageListDto> carDamageListDtos, List<CarDamage> carDamages){
		for (int i = 0; i < carDamages.size(); i++) {
			carDamageListDtos.get(i).setCarId(carDamages.get(i).getCar().getCarId());
		}
	}

	private void checkIfCarExistsById(int carId) throws BusinessException {
		this.carService.isCarExistsById(carId);
	}
	
	
	

}

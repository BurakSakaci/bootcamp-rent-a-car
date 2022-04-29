package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.RentService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetCarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.listdto.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.listdto.RentListDto;
import com.turkcell.rentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CarMaintenanceException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACar.entities.concrates.CarMaintenance;
import com.turkcell.rentACar.entities.concrates.Rent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {
	private final CarMaintenanceDao carMaintenanceDao;
	private final RentService rentService;
	private final ModelMapperService modelMapperService;
	private final CarService carService;

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, @Lazy RentService rentService,
			ModelMapperService modelMapperService, CarService carService) {
		this.carMaintenanceDao = carMaintenanceDao;
		this.rentService = rentService;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll() {
		
		List<CarMaintenance> carMaintenances = carMaintenanceDao.findAll();

        List<CarMaintenanceListDto> carMaintenanceListDtos = carMaintenances.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());
        mappinMaintenance(carMaintenances, carMaintenanceListDtos);

        return new SuccessDataResult<>(carMaintenanceListDtos, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(int id) throws CarMaintenanceException {
		
		checkIfCarMaintenanceExistsById(id);
		
		CarMaintenance foundCarMaintenance = carMaintenanceDao.getById(id);
		
		GetCarMaintenanceDto response = this.modelMapperService.forDto().map(foundCarMaintenance, GetCarMaintenanceDto.class);
		response.setCarId(foundCarMaintenance.getCar().getCarId());
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) throws BusinessException {
		
		checkIfCarIsExistsById(carId);
		
		List<CarMaintenance> result = this.carMaintenanceDao.findByCar_CarId(carId);
		
		List<CarMaintenanceListDto> response = result.stream()
				.map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());
		mappinMaintenance(result, response);
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarIsExistsById(createCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);
		
		checkIfCarIsRented(carMaintenance);
		
		carMaintenance.setCarMaintenanceId(0);
		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest)
			throws CarMaintenanceException {
		
		checkIfCarMaintenanceExistsById(deleteCarMaintenanceRequest.getCarMaintenanceId());
		
		this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.getCarMaintenanceId());
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarMaintenanceExistsById(updateCarMaintenanceRequest.getCarMaintenanceId());
		checkIfCarIsExistsById(updateCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);
		
		checkIfCarIsRented(carMaintenance);

		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}

	private void checkIfCarMaintenanceExistsById(int id) throws CarMaintenanceException{
		if (!this.carMaintenanceDao.existsById(id)) {
			throw new CarMaintenanceException(BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_NOT_FOUND);
		}
	}

	private void checkIfCarIsRented(CarMaintenance carMaintenance) throws BusinessException {
		DataResult<List<RentListDto>> result = this.rentService.getBy_CarId(carMaintenance.getCar().getCarId());
		List<Rent> response = result.getData().stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, Rent.class)).collect(Collectors.toList());
		
		for(Rent rents: response) {
			if( (rents.getFinishDate().isAfter(carMaintenance.getReturnDate()) && rents.getStartDate().isBefore(carMaintenance.getReturnDate())) || rents.getFinishDate()==null ) {
				throw new BusinessException(BusinessMessages.CarMaintenanceMessages.CAR_IS_RENTED);
			}
		}
		
	}
	
	private void checkIfCarIsExistsById(int carId) throws BusinessException {
		this.carService.isCarExistsById(carId);
	}
	
	private void mappinMaintenance(List<CarMaintenance> carMaintenances, List<CarMaintenanceListDto> carMaintenanceListDtos) {
		for (int i = 0; i < carMaintenances.size(); i++) {
			carMaintenanceListDtos.get(i).setCarId(carMaintenances.get(i).getCar().getCarId());
		}
	}
	

}

package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetCarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.listdto.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CarMaintenanceException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarMaintenanceService {
	DataResult<List<CarMaintenanceListDto>> getAll();

	DataResult<GetCarMaintenanceDto> getById(int id) throws CarMaintenanceException;

	DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) throws BusinessException;

	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;

	Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws CarMaintenanceException;

	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;

}

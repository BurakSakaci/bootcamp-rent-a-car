package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.api.models.CreateRentModel;
import com.turkcell.rentACar.api.models.RentEndModel;
import com.turkcell.rentACar.api.models.UpdateRentModel;
import com.turkcell.rentACar.business.dtos.getdto.GetRentDto;
import com.turkcell.rentACar.business.dtos.listdto.RentListDto;
import com.turkcell.rentACar.business.requests.delete.DeleteRentRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.RentException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concrates.Rent;

public interface RentService {
	DataResult<List<RentListDto>> getAll();
	DataResult<GetRentDto> getById(int id) throws RentException;
	DataResult<List<RentListDto>> getBy_CarId(int carId) throws BusinessException;
	DataResult<Rent> addRentForIndividualCustomer(CreateRentModel createRentModel) throws BusinessException;
	DataResult<Rent> addRentForCorporateCustomer(CreateRentModel createRentModel) throws BusinessException;
	Result delete(DeleteRentRequest deleteRentRequest) throws RentException;
	Result update(UpdateRentModel updateRentModel) throws BusinessException;
	Result endRent(RentEndModel rentEndModel) throws BusinessException;
	
	Rent getByIdAsEntityRent(int rentId);
}

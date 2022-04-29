package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.getdto.GetOrderedAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.listdto.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.create.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concrates.OrderedAdditionalService;

public interface OrderedAdditionalServiceService {
	
	DataResult<List<OrderedAdditionalServiceListDto>> getAll();
	DataResult<GetOrderedAdditionalServiceDto> getById(int id) throws BusinessException;
	DataResult<OrderedAdditionalService> add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException;
	Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException;
	Result update (UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException;
	
	OrderedAdditionalService getByIdAsEntity (int id);
	
}

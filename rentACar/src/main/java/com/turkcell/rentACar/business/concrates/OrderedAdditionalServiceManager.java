package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.getdto.GetOrderedAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.listdto.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.create.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACar.entities.concrates.AdditionalService;
import com.turkcell.rentACar.entities.concrates.OrderedAdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService{
	
	private final OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private final ModelMapperService modelMapperService;
	private final AdditionalServiceService additionalServiceService;
	
	@Autowired
	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
			ModelMapperService modelMapperService, AdditionalServiceService additionalServiceService) {
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.modelMapperService = modelMapperService;
		this.additionalServiceService = additionalServiceService;
	}

	
	@Override
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
		
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.findAll();
		
		List<OrderedAdditionalServiceListDto> response = result.stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetOrderedAdditionalServiceDto> getById(int id) throws BusinessException {
		
		checkIfOrderedAdditionalServiceExistsById(id);
		
		OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(id);
		GetOrderedAdditionalServiceDto response = this.modelMapperService.forDto().map(orderedAdditionalService, GetOrderedAdditionalServiceDto.class);
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<OrderedAdditionalService> add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
		
		List<AdditionalService> additionalServices = new ArrayList<>();
		
		for (Integer additionalServiceId : createOrderedAdditionalServiceRequest.getAdditionalServices()) {
			GetAdditionalServiceDto serviceData = this.additionalServiceService.getById(additionalServiceId).getData();
			AdditionalService map = this.modelMapperService.forDto().map(serviceData, AdditionalService.class);
			additionalServices.add(map);
		}
		
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().
				map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		orderedAdditionalService.setAdditionalServices(additionalServices);
		
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		
		return new SuccessDataResult<>(orderedAdditionalService, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException {
		
		checkIfOrderedAdditionalServiceExistsById(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		
		this.orderedAdditionalServiceDao.deleteById(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException {
		
		checkIfOrderedAdditionalServiceExistsById(updateOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}
	
	
	private void checkIfOrderedAdditionalServiceExistsById(int id) throws BusinessException {
		if(!this.orderedAdditionalServiceDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.OrderedAdditionalServiceMessages.ORDERED_ADDITIONAL_SERVICE_NOT_FOUND);
		}
	}

	@Override
	public OrderedAdditionalService getByIdAsEntity(int id) {
		return this.orderedAdditionalServiceDao.getById(id);
	}
	
	
	

}

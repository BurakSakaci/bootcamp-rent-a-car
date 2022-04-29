package com.turkcell.rentACar.business.concrates;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CacheService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.listdto.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.AdditionalServiceException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACar.entities.concrates.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService{
	
	private final AdditionalServiceDao additionalServiceDao;
	private final ModelMapperService modelMapperService;
	private final CacheService cacheService;
	
	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService, CacheService cacheService) {
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
		this.cacheService = cacheService;
	}

	@Cacheable(value = "additional_services")
	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll() {
		
		List<AdditionalService> result = this.additionalServiceDao.findAll();
		
		List<AdditionalServiceListDto> response = result.stream().map(additionalService -> this.modelMapperService.forDto()
				.map(additionalService, AdditionalServiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Cacheable(value = "additional_services", key = "#id")
	@Override
	public DataResult<GetAdditionalServiceDto> getById(int id) throws BusinessException {
		
		checkIfAdditionalServiceExistsById(id);
		
		AdditionalService additionalService = this.additionalServiceDao.getById(id);
		GetAdditionalServiceDto response = this.modelMapperService.forDto().map(additionalService, GetAdditionalServiceDto.class);
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@CachePut(value = "additional_services")
	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		
		checkIfAdditionalServiceExistsByName(createAdditionalServiceRequest.getAdditionalServiceName());
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
		
		this.additionalServiceDao.save(additionalService);

		evictAllcachesAtIntervals();

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@CacheEvict(value = "additional_services", allEntries = true)
	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
		
		checkIfAdditionalServiceExistsById(deleteAdditionalServiceRequest.getAdditionalServiceId());
		
		this.additionalServiceDao.deleteById(deleteAdditionalServiceRequest.getAdditionalServiceId());
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@CachePut(value = "additional_services")
	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
		
		checkIfAdditionalServiceExistsById(updateAdditionalServiceRequest.getAdditionalServiceId());
		checkIfAdditionalServiceExistsByName(updateAdditionalServiceRequest.getAdditionalServiceName());
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
		
		this.additionalServiceDao.save(additionalService);

		evictAllcachesAtIntervals();
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}
	
	@Scheduled(fixedRate = 2000)
	public void evictAllcachesAtIntervals() {
		this.cacheService.evictAllCaches();
	}
	
	
	private void checkIfAdditionalServiceExistsById(int id) throws BusinessException {
		if(!this.additionalServiceDao.existsById(id)) {
			throw new AdditionalServiceException(BusinessMessages.AdditionalServiceMessages.ADDITIONAL_SERVICE_NOT_FOUND);
		}
	}	
	
	private void checkIfAdditionalServiceExistsByName(String AdditionalServiceName) throws BusinessException {
		if(this.additionalServiceDao.existsByAdditionalServiceName(AdditionalServiceName)) {
			throw new AdditionalServiceException(BusinessMessages.AdditionalServiceMessages.ADDITIONAL_SERVICE_ALREADY_EXISTS);
		}
	}
	
}

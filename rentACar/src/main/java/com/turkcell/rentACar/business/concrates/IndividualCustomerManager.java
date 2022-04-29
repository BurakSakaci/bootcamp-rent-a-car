package com.turkcell.rentACar.business.concrates;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetIndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.listdto.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.IndividualCustomerException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACar.entities.concrates.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService{
	
	private final IndividualCustomerDao individualCustomerDao;
	private final ModelMapperService modelMapperService;
	
	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,
			ModelMapperService modelMapperService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<IndividualCustomerListDto>> getAll() {
		List<IndividualCustomer> result = this.individualCustomerDao.findAll();
		List<IndividualCustomerListDto> response = result.stream().map(individualCustomer -> this.modelMapperService
				.forDto().map(individualCustomer, IndividualCustomerListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<>(response,BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetIndividualCustomerDto> getById(int id) throws IndividualCustomerException {
		
		checkIfExistsById(id);
		
		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);
		GetIndividualCustomerDto response = this.modelMapperService.forDto().map(individualCustomer,
				GetIndividualCustomerDto.class);
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws IndividualCustomerException {
		
		checkIfIndividualCustomerExistsByEmail(createIndividualCustomerRequest.getEmail());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(createIndividualCustomerRequest, IndividualCustomer.class);
		
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) throws IndividualCustomerException {
		
		checkIfExistsById(id);
		
		this.individualCustomerDao.deleteById(id);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws IndividualCustomerException {
		
		checkIfExistsById(updateIndividualCustomerRequest.getCustomerId());
		checkIfIndividualCustomerExistsByEmail(updateIndividualCustomerRequest.getEmail());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(updateIndividualCustomerRequest, IndividualCustomer.class);
		
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result individualCustomerExistsById(int id) {
		if(!individualCustomerDao.existsById(id)) {
			return new ErrorResult(BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND);
		}
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}
	
	private void checkIfExistsById(int id) throws IndividualCustomerException {
		if(!this.individualCustomerDao.existsById(id)) {
			throw new IndividualCustomerException(BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND);
		}
	}
	
	private void checkIfIndividualCustomerExistsByEmail(String email) throws IndividualCustomerException {
		if(this.individualCustomerDao.existsByEmail(email)) {
			throw new IndividualCustomerException(BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_EMAIL_ALREADY_EXISTS);
		}
	}
	

}

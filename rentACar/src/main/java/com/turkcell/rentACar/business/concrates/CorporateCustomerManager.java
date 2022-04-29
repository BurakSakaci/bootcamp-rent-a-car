package com.turkcell.rentACar.business.concrates;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetCorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.listdto.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CorporateCustomerException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACar.entities.concrates.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService{
	private final CorporateCustomerDao corporateCustomerDao;
	private final ModelMapperService modelMapperService;
	
	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CorporateCustomerListDto>> getAll() {
		
		List<CorporateCustomer> result = this.corporateCustomerDao.findAll();
		
		List<CorporateCustomerListDto> response = result.stream().map(corporateCustomer -> this.modelMapperService
				.forDto().map(corporateCustomer, CorporateCustomerListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCorporateCustomerDto> getById(int id) throws CorporateCustomerException {
		
		checkIfcorporateCustomerExistsById(id);
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(id);
		GetCorporateCustomerDto response = this.modelMapperService.forDto().map(corporateCustomer,
				GetCorporateCustomerDto.class);
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws CorporateCustomerException {
		
		checkIfCorporateCustomerExistsByEmail(createCorporateCustomerRequest.getEmail());
		checkIfcorporateCustomerExistsByTaxNumber(createCorporateCustomerRequest.getTaxNumber());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,
				CorporateCustomer.class);
		
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}
	
	
	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws CorporateCustomerException {
		
		checkIfcorporateCustomerExistsById(updateCorporateCustomerRequest.getCustomerId());
		checkIfCorporateCustomerExistsByEmail(updateCorporateCustomerRequest.getEmail());
		checkIfCorporateCustomerExistsByEmail(updateCorporateCustomerRequest.getTaxNumber());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
				CorporateCustomer.class);
		
		this.corporateCustomerDao.save(corporateCustomer);
	
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) throws CorporateCustomerException {
		
		checkIfcorporateCustomerExistsById(id);
		
		this.corporateCustomerDao.deleteById(id);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	

	@Override
	public Result corporateCustomerExistsById(int id) {
		if(!corporateCustomerDao.existsById(id)) {
			return new ErrorResult(BusinessMessages.CorporateCustomerMessages.CORPORATE_CUSTOMER_NOT_FOUND);
		}
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}
	
	private void checkIfCorporateCustomerExistsByEmail(String email) throws CorporateCustomerException {
		if(this.corporateCustomerDao.existsByEmail(email)) {
			throw new CorporateCustomerException(BusinessMessages.CorporateCustomerMessages.CORPORATE_CUSTOMER_EMAIL_ALREADY_EXISTS);
		}
	}
	
	private void checkIfcorporateCustomerExistsByTaxNumber(String taxNumber) throws CorporateCustomerException {
		if(this.corporateCustomerDao.existsByTaxNumber(taxNumber)) {
			throw new CorporateCustomerException(BusinessMessages.CorporateCustomerMessages.CORPORATE_CUSTOMER_TAX_NUMBER_ALREADY_EXISTS);
		}
		
	}
	
	private void checkIfcorporateCustomerExistsById(int id) throws CorporateCustomerException {
		if(!corporateCustomerDao.existsById(id)) {
			throw new CorporateCustomerException(BusinessMessages.CorporateCustomerMessages.CORPORATE_CUSTOMER_NOT_FOUND);
		}
	}
	
	
	
	
	

}

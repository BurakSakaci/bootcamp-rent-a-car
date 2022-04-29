package com.turkcell.rentACar.business.concrates;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.CreditCardService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.listdto.CreditCardListDto;
import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CreditCardException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CustomerException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CreditCardDao;
import com.turkcell.rentACar.entities.concrates.CreditCard;

@Service
public class CreditCardManager implements CreditCardService {
	private final CreditCardDao creditCardDao;
	private final ModelMapperService modelMapperService;
	private final CustomerService customerService;

	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService,
			CustomerService customerService) {
		this.creditCardDao = creditCardDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public DataResult<List<CreditCardListDto>> getAll() {
		List<CreditCard> result = this.creditCardDao.findAll();
		List<CreditCardListDto> response = result.stream()
				.map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CreditCardListDto>> getByCustomerId(int customerId) throws BusinessException {
		
		checkIfCustomerExistsById(customerId);
		
		List<CreditCard> result = this.creditCardDao.findByCustomer_UserId(customerId);

		List<CreditCardListDto> response = result.stream()
				.map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<> (response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest, int customerId) throws BusinessException {
		
		checkIfCustomerExistsById(customerId);
		checkIfCardExists(createCreditCardRequest.getCvv(), createCreditCardRequest.getCardNo());
		
		CreditCard creditCard = this.modelMapperService.forDto().map(createCreditCardRequest, CreditCard.class);
		creditCard.setCustomer(this.customerService.getByIdAsEntityCustomer(customerId));
		
		this.creditCardDao.save(creditCard);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException {
		
		checkIfCreditCardExistsById(updateCreditCardRequest.getCardId());
		checkIfCustomerExistsById(updateCreditCardRequest.getCustomerId());
		checkIfCardExists(updateCreditCardRequest.getCvv(), updateCreditCardRequest.getCardNo());
		
		CreditCard creditCard = this.modelMapperService.forDto().map(updateCreditCardRequest, CreditCard.class);
		this.creditCardDao.save(creditCard);
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) throws CreditCardException {
		
		checkIfCreditCardExistsById(deleteCreditCardRequest.getCardId());
		
		this.creditCardDao.deleteById(deleteCreditCardRequest.getCardId());

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}
	
	private void checkIfCreditCardExistsById(int id) throws CreditCardException {
		if(!this.creditCardDao.existsById(id)) {
			throw new CreditCardException(BusinessMessages.CreditCardMessages.CREDIT_CARD_NOT_FOUND);
		}
	}
	
	private void checkIfCardExists(int cvv, String cardNo) throws CreditCardException {
		if(this.creditCardDao.existsByCvvAndCardNo(cvv, cardNo)) {
			throw new CreditCardException(BusinessMessages.CreditCardMessages.CARD_ALREADY_EXISTS); 
		}
		
		
	}
	
	private void checkIfCustomerExistsById(int id) throws BusinessException {
		if(!this.customerService.getById(id).isSuccess()) {
			throw new CustomerException(BusinessMessages.CreditCardMessages.CUSTOMER_NOT_FOUND);
		}
	
	}
	

}

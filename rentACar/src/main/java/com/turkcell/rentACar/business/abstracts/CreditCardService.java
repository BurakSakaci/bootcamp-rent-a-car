package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetCreditCardDto;
import com.turkcell.rentACar.business.dtos.listdto.CreditCardListDto;
import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CreditCardService {
	DataResult<List<CreditCardListDto>> getAll();
	DataResult<List<CreditCardListDto>> getByCustomerId(int customerId) throws BusinessException;
	Result add(CreateCreditCardRequest createCreditCardRequest, int customerId) throws BusinessException;
	Result update(UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException;
	Result delete(DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException;
}

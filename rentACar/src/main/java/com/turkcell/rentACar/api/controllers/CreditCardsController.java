package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CreditCardService;
import com.turkcell.rentACar.business.dtos.listdto.CreditCardListDto;
import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/credit-cards")
public class CreditCardsController {
	private CreditCardService creditCardService;
	
	@Autowired
	public CreditCardsController(CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CreditCardListDto>> getAll(){
		return this.creditCardService.getAll();
	}
	@GetMapping("/getByCustomerId")
	public DataResult<List<CreditCardListDto>> getByCustomerId(@RequestParam int customerId) throws BusinessException{
		return this.creditCardService.getByCustomerId(customerId);
	}
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCreditCardRequest createCreditCardRequest, int customerId) throws BusinessException{
		return this.creditCardService.add(createCreditCardRequest, customerId);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException{
		return this.creditCardService.update(updateCreditCardRequest);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException{
		return this.creditCardService.delete(deleteCreditCardRequest);
	}
	
}

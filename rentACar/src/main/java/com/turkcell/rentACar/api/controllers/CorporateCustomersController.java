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

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.dtos.getdto.GetCorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.listdto.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CorporateCustomerException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporate_customers")
public class CorporateCustomersController {
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CorporateCustomerListDto>> getAll() {
		return corporateCustomerService.getAll();
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetCorporateCustomerDto> getById(@RequestParam int id) throws CorporateCustomerException{
		return corporateCustomerService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest)
			throws CorporateCustomerException {
		return corporateCustomerService.add(createCorporateCustomerRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest)
			throws CorporateCustomerException {
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) throws CorporateCustomerException {
		return corporateCustomerService.delete(id);
	}

}
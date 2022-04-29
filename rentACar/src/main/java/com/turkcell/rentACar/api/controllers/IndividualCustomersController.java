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

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.dtos.getdto.GetIndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.listdto.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.IndividualCustomerException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individual_customers")
public class IndividualCustomersController {
	private IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}

	@GetMapping("/getAll")
	public DataResult<List<IndividualCustomerListDto>> getAll() {
		return individualCustomerService.getAll();
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetIndividualCustomerDto> getById(@RequestParam int id) throws IndividualCustomerException {
		return individualCustomerService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest)
			throws IndividualCustomerException {
		return individualCustomerService.add(createIndividualCustomerRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest)
			throws IndividualCustomerException {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) throws IndividualCustomerException {
		return individualCustomerService.delete(id);
	}

}
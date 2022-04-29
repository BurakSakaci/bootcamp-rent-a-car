package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.api.models.CardSaverEnum;
import com.turkcell.rentACar.api.models.CreateRentModel;
import com.turkcell.rentACar.api.models.RentEndModel;
import com.turkcell.rentACar.api.models.UpdateRentModel;
import com.turkcell.rentACar.business.abstracts.RentService;
import com.turkcell.rentACar.business.dtos.getdto.GetRentDto;
import com.turkcell.rentACar.business.dtos.listdto.RentListDto;
import com.turkcell.rentACar.business.requests.delete.DeleteRentRequest;
import com.turkcell.rentACar.business.requests.end.EndRentRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rents")
public class RentsController {

	private RentService rentService;

	@Autowired
	public RentsController(RentService rentService) {
		this.rentService = rentService;
	}

	@GetMapping("/getAll")
	public DataResult<List<RentListDto>> getAll() {
		return this.rentService.getAll();
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetRentDto> getById(@RequestParam int id) throws BusinessException{
		return this.rentService.getById(id);
	}

	@GetMapping("/getBy_CarId/{carId}")
	public DataResult<List<RentListDto>> getBy_CarId(@RequestParam("{carId}") int carId) throws BusinessException {
		return this.rentService.getBy_CarId(carId);
	}

	/*
	@PostMapping("/addRentForIndividualCustomer")
	public Result addRentForIndividualCustomer(@RequestBody @Valid CreateRentModel createRentModel) throws BusinessException{
		return this.rentService.addRentForIndividualCustomer(createRentModel);
	}

	@PostMapping("/addRentForCorporateCustomer")
	public Result addRentForCorporateCustomer(@RequestBody @Valid CreateRentModel createRentModel) throws BusinessException{
		return this.rentService.addRentForCorporateCustomer(createRentModel);
	}
	*/
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteRentRequest deleteRentRequest) throws BusinessException{
		return this.rentService.delete(deleteRentRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateRentModel updateRentModel) throws BusinessException{
		return this.rentService.update(updateRentModel);
	}
	/*
	@PutMapping("/endWithoutPayment")
	public Result endWithoutPayment(@RequestBody @Valid EndRentRequest endRentRequest) throws BusinessException{
		return this.rentService.endWithoutPayment(endRentRequest);
	}
	*/
	
	@PutMapping("/endRent")
	public Result endRent(@RequestBody @Valid RentEndModel rentEndModel) throws BusinessException {
		return this.rentService.endRent(rentEndModel);
	}


}

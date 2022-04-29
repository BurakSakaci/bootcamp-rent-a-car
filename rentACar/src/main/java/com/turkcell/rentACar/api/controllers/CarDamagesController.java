package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import com.turkcell.rentACar.core.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.dtos.getdto.GetCarDamageDto;
import com.turkcell.rentACar.business.dtos.listdto.CarDamageListDto;
import com.turkcell.rentACar.business.requests.create.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CarDamageException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/car-damages")
public class CarDamagesController {

	private CarDamageService carDamageService;

	@Autowired
	public CarDamagesController(CarDamageService carDamageService) {
		super();
		this.carDamageService = carDamageService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CarDamageListDto>> getAll(){
		return this.carDamageService.getAll();
	}

	@GetMapping("/get/{id}")
	public DataResult<GetCarDamageDto> getById(@RequestParam int id) throws CarDamageException{
		return this.carDamageService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarDamageRequest createCarDamageRequest) throws BusinessException {
		return this.carDamageService.add(createCarDamageRequest);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) throws CarDamageException {
		return this.carDamageService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {
		return this.carDamageService.update(updateCarDamageRequest);
	}

}

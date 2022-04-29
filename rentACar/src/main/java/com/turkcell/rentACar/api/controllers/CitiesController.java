package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.dtos.getdto.GetCityDto;
import com.turkcell.rentACar.business.dtos.listdto.CityListDto;
import com.turkcell.rentACar.business.requests.create.CreateCityRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCityRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCityRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CityException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RequestMapping("/api/cities")
@RestController
public class CitiesController {
	private CityService cityService;

	public CitiesController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<CityListDto>> getAll() {
		return this.cityService.getAll();
	}
	@GetMapping("/getById")
	public DataResult<GetCityDto> getById(@RequestParam int id) throws CityException {
		return this.cityService.getById(id);
	}
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) throws CityException {
		return this.cityService.add(createCityRequest);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCityRequest deleteCityRequest) throws CityException {
		return this.cityService.delete(deleteCityRequest);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCityRequest updateCityRequest) throws CityException {
		return this.cityService.update(updateCityRequest);
	}

}
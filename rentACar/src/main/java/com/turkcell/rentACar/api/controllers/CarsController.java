package com.turkcell.rentACar.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.getdto.GetCarDto;
import com.turkcell.rentACar.business.dtos.listdto.CarListDto;
import com.turkcell.rentACar.business.requests.create.CreateCarRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.CarException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concrates.Car;


@RestController
@RequestMapping("/api/cars")
public class CarsController {
	private CarService carService;

	public CarsController(CarService carService) {
		this.carService = carService;
	}
	
	
	@GetMapping("/getall")
	public DataResult<List<CarListDto>> getAll() {
		return this.carService.getAll();
	}

	
	@GetMapping("/getById/{carId}")
	public DataResult<GetCarDto> getCarById(@PathVariable("carId") int id) throws BusinessException{
		return this.carService.getById(id);
	}
	
	@GetMapping("/getAllPaged/{pageNo}/{pageSize}")
	DataResult<List<GetCarDto>> getAllPaged(@PathVariable("pageNo")int pageNo, @PathVariable("pageSize")int pageSize) throws BusinessException {
		return this.carService.getAllPaged(pageNo, pageSize);
	}
	
	@GetMapping("/getAllSorted/{orderOfSort}")
	DataResult<List<GetCarDto>> getAllSorted(@RequestParam("orderOfSort")String orderOfSort) throws CarException{
		return this.carService.getAllSorted(orderOfSort);
	}
	
	@GetMapping("/findByDailyPriceLessThan/{requestedPrice}")
	DataResult<List<GetCarDto>> findByDailyPriceLessThan(@PathVariable("requestedPrice") int requestedPrice){
		return this.carService.findByDailyPriceLessThan(requestedPrice);
	}
	
	@GetMapping("/findByDailyPriceGreaterThan/{requestedPrice}")
	DataResult<List<GetCarDto>> findByDailyPriceGreaterThan(@PathVariable("requestedPrice") int requestedPrice){
		return this.carService.findByDailyPriceGreaterThan(requestedPrice);
	}
	
	@GetMapping("/findByDailyPriceBetween/{minValue}/{maxValue}")
	DataResult<List<GetCarDto>> findByDailyPriceBetween(@PathVariable("minValue") int minValue, @PathVariable("maxValue") int maxValue) throws BusinessException{
		return this.carService.findByDailyPriceBetween(minValue, maxValue);
	}
	
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) throws BusinessException{
		return this.carService.add(createCarRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarRequest deleteCarRequest) throws BusinessException{
		return this.carService.delete(deleteCarRequest);
	}

	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest) throws BusinessException{
		return this.carService.update(updateCarRequest);
	}

	@GetMapping("/getByBrand_Id/{brandId}")
	public DataResult<List<GetCarDto>> getByBrand_Id(@PathVariable("brandId") int brandId) throws BusinessException{
		return this.carService.getByBrand_Id(brandId);
	}
	
	@GetMapping("/getByColor_Id/{colorId}")
	public DataResult<List<GetCarDto>> getByColor_Id(@PathVariable("colorId") int colorId) throws BusinessException{
		return this.carService.getByColor_Id(colorId);
	}
	
	@GetMapping("/getByBrand_IdAndColor_Id/{brandId}/{colorId}")
	public DataResult<List<GetCarDto>> getByBrand_IdAndColor_Id(@PathVariable("brandId") int brandId, @PathVariable("colorId") int colorId) throws BusinessException{
		return this.carService.getByBrand_IdAndColor_Id(brandId, colorId);
	}
	
	
	
	
	
	

}

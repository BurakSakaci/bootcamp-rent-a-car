package com.turkcell.rentACar.api.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.dtos.getdto.GetCarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.listdto.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
@RestController
@RequestMapping("/api/car-maintenances")
public class CarMaintenancesController {
	private CarMaintenanceService carMaintenanceService;

	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}

	@GetMapping("/getall")
	public DataResult<List<CarMaintenanceListDto>> getAll() {
		return this.carMaintenanceService.getAll();
	}
	@GetMapping("/getById/{car_maintenance_id}")
	public DataResult<GetCarMaintenanceDto> getById(@PathVariable("car_maintenance_id") int id) throws BusinessException{
		return this.carMaintenanceService.getById(id);
	}
	
	/*@GetMapping("/findByReturnDateBetween/{start_date}/{end_date}")
	public DataResult<List<CarMaintenanceListDto>> findByReturnDateBetween(@PathVariable("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @PathVariable("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws BusinessException{
		return this.carMaintenanceService.findByReturnDateBetween(startDate, endDate);
	}
	*/
	
	@GetMapping("/getByCarId/{carId}")
	public DataResult<List<CarMaintenanceListDto>> getByCarId(@PathVariable("carId") int carId) throws BusinessException{
		return this.carMaintenanceService.getByCarId(carId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException{
		return this.carMaintenanceService.add(createCarMaintenanceRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException{
		return this.carMaintenanceService.delete(deleteCarMaintenanceRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException{
		return this.carMaintenanceService.update(updateCarMaintenanceRequest);
	}
	
}

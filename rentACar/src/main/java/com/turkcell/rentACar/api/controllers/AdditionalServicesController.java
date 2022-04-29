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

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.dtos.getdto.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.listdto.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additional-services")
public class AdditionalServicesController {
	
	private AdditionalServiceService additionalServiceService;

	@Autowired
	public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
		this.additionalServiceService = additionalServiceService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<AdditionalServiceListDto>> getAll(){
		return this.additionalServiceService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<GetAdditionalServiceDto> getById(@RequestParam int id) throws BusinessException{
		return this.additionalServiceService.getById(id);
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		return this.additionalServiceService.add(createAdditionalServiceRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
		return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
		return this.additionalServiceService.update(updateAdditionalServiceRequest);
	}

}

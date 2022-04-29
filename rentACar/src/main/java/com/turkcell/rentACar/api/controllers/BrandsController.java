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
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.getdto.GetBrandDto;
import com.turkcell.rentACar.business.dtos.listdto.BrandListDto;
import com.turkcell.rentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.update.UpdateBrandRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concrates.Brand;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	private BrandService brandService;
	
	
	@Autowired
	public BrandsController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}

	@GetMapping("/getall")
	public DataResult<List<BrandListDto>> getAll() {
		return this.brandService.getAll();
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetBrandDto> getBrandById(@PathVariable int id) throws BusinessException{
		return this.brandService.getById(id);
				
	}
	
	
	@PostMapping("/add")
	public DataResult<Brand> add(@RequestBody @Valid CreateBrandRequest createBrandRequest) throws BusinessException{
		return this.brandService.add(createBrandRequest);
	}
	
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteBrandRequest deleteBrandRequest) throws BusinessException{
		return this.brandService.delete(deleteBrandRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) throws BusinessException{
		return this.brandService.update(updateBrandRequest);
	}


	


}

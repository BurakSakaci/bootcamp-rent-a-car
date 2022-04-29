package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetBrandDto;
import com.turkcell.rentACar.business.dtos.listdto.BrandListDto;
import com.turkcell.rentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.update.UpdateBrandRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.BrandException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concrates.Brand;

public interface BrandService {
	DataResult<List<BrandListDto>> getAll();
	DataResult<GetBrandDto> getById(int id) throws BrandException;
	DataResult<Brand> add(CreateBrandRequest createBrandRequest) throws BrandException;
	Result delete(DeleteBrandRequest deleteBrandRequest) throws BrandException;
	Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;
}

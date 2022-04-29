package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.abstracts.CacheService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetBrandDto;
import com.turkcell.rentACar.business.dtos.listdto.BrandListDto;
import com.turkcell.rentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.update.UpdateBrandRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.BrandException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACar.entities.concrates.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandManager implements BrandService {
	private final BrandDao brandDao;
	private final ModelMapperService modelMapperService;
	private final CacheService cacheService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService, CacheService cacheService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
		this.cacheService = cacheService;
	}

	@Cacheable(value = "brands")
	@Override
	public DataResult<List<BrandListDto>> getAll() {
		
		
		List<Brand> result = this.brandDao.findAll();
		
		List<BrandListDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());
				
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Cacheable(value = "brands", key = "#id")
	@Override
	public DataResult<GetBrandDto> getById(int id) throws BrandException {
		
		checkIfBrandExistsById(id);
		
		Brand foundBrand = brandDao.getById(id);
		GetBrandDto response = this.modelMapperService.forDto().map(foundBrand, GetBrandDto.class);
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);

	}

	@CachePut(value = "brands")
	@Override
	public DataResult<Brand> add(CreateBrandRequest createBrandRequest) throws BrandException {
		
		checkIfBrandExistsByName(createBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		
		this.brandDao.save(brand);
		
		evictAllcachesAtIntervals();
		
		return new SuccessDataResult<>(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@CacheEvict(value = "brands", allEntries = true)
	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BrandException {
		
		checkIfBrandExistsById(deleteBrandRequest.getBrandId());
		
		this.brandDao.deleteById(deleteBrandRequest.getBrandId());
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@CachePut(value = "brands")
	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
		
		checkIfBrandExistsById(updateBrandRequest.getBrandId());
		checkIfBrandExistsByName(updateBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		
		this.brandDao.save(brand);
		
		evictAllcachesAtIntervals();
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}
	
	@Scheduled(fixedRate = 2000)
	public void evictAllcachesAtIntervals() {
		this.cacheService.evictAllCaches();
	}
	
	

	private void checkIfBrandExistsByName(String brandName) throws BrandException {
		if (this.brandDao.existsByBrandName(brandName)) {
			throw new BrandException(BusinessMessages.BrandMessages.BRAND_ALREADY_EXISTS);
		}
	}

	private void checkIfBrandExistsById(int id) throws BrandException {
		if (!this.brandDao.existsById(id)) {
			throw new BrandException(BusinessMessages.BrandMessages.BRAND_NOT_FOUND);
		}
	}

}



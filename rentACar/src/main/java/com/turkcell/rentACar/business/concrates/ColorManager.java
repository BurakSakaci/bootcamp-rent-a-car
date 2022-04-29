package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CacheService;
import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.getdto.GetColorDto;
import com.turkcell.rentACar.business.dtos.listdto.ColorListDto;
import com.turkcell.rentACar.business.requests.create.CreateColorRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteColorRequest;
import com.turkcell.rentACar.business.requests.update.UpdateColorRequest;
import com.turkcell.rentACar.core.exceptions.entityExceptions.ColorException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACar.entities.concrates.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {
	private final ColorDao colorDao;
	private final ModelMapperService modelMapperService;
	private final CacheService cacheService;

	
	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService, CacheService cacheService) {
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
		this.cacheService = cacheService;
	}

	@Cacheable(value = "colors")
	@Override
	public DataResult<List<ColorListDto>> getAll() {
		
		List<Color> result = this.colorDao.findAll();
		
		List<ColorListDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Cacheable(value = "colors", key = "#id")
	@Override
	public DataResult<GetColorDto> getById(int id) throws ColorException {
		
		checkIfColorExistsById(id);
		
		Color foundColor = colorDao.getById(id);
		GetColorDto response = this.modelMapperService.forDto().map(foundColor, GetColorDto.class);
		
		return new SuccessDataResult<>(response, BusinessMessages.GlobalMessages.DATA_GETTED_SUCCESSFULLY);
	}

	@CachePut(value = "colors")
	@Override
	public Result add(CreateColorRequest createColorRequest) throws ColorException {
		
		checkIfColorExistsByName(createColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		this.colorDao.save(color);
		
		evictAllcachesAtIntervals();

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}
	
	@CachePut(value = "colors")
	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws ColorException {
		
		checkIfColorExistsById(updateColorRequest.getColorId());
		checkIfColorExistsByName(updateColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		
		this.colorDao.save(color);
		
		evictAllcachesAtIntervals();
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
	}

	@CacheEvict(value = "colors", allEntries = true)
	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws ColorException {
		
		checkIfColorExistsById(deleteColorRequest.getColorId());
		
		this.colorDao.deleteById(deleteColorRequest.getColorId());
		
		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@Scheduled(fixedRate = 2000)
	public void evictAllcachesAtIntervals() {
		this.cacheService.evictAllCaches();
	}

	private void checkIfColorExistsById(int id) throws ColorException {
		if (!this.colorDao.existsById(id)) {
			throw new ColorException(BusinessMessages.ColorMessages.COLOR_NOT_FOUND);
		}
	}

	private void checkIfColorExistsByName(String colorName) throws ColorException {
		if (this.colorDao.existsByColorName(colorName)) {
			throw new ColorException(BusinessMessages.ColorMessages.COLOR_ALREADY_EXISTS);
		}
	}

}

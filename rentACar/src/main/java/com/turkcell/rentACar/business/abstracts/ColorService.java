package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetColorDto;
import com.turkcell.rentACar.business.dtos.listdto.ColorListDto;
import com.turkcell.rentACar.business.requests.create.CreateColorRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteColorRequest;
import com.turkcell.rentACar.business.requests.update.UpdateColorRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.ColorException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface ColorService {
	DataResult<List<ColorListDto>> getAll();
	DataResult<GetColorDto> getById(int id) throws ColorException;
	Result add(CreateColorRequest createColorRequest) throws ColorException;
	Result delete(DeleteColorRequest deleteColorRequest) throws ColorException;
	Result update(UpdateColorRequest updateColorRequest) throws BusinessException;
	
}

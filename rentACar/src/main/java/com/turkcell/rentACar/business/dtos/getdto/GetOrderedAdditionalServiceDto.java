package com.turkcell.rentACar.business.dtos.getdto;

import java.util.List;

import com.turkcell.rentACar.business.dtos.listdto.AdditionalServiceListDto;
import com.turkcell.rentACar.entities.concrates.AdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderedAdditionalServiceDto {

	
	private List<AdditionalServiceListDto> additionalServices;
	
}

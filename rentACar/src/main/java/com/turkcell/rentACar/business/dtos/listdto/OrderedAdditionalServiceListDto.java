package com.turkcell.rentACar.business.dtos.listdto;

import java.util.List;
import java.util.Set;

import com.turkcell.rentACar.entities.concrates.AdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceListDto {

	private int orderedAdditionalServiceId;
	private List<AdditionalServiceListDto> additionalServices;

	
}

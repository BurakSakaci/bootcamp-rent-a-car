package com.turkcell.rentACar.business.dtos.listdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalServiceListDto {
	
	private int additionalServiceId;
	
	private String additionalServiceName;

	private int additionalServiceDailyPrice;
}

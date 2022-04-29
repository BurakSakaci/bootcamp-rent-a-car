package com.turkcell.rentACar.business.dtos.getdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAdditionalServiceDto {

	private int additionalServiceId;

	private String additionalServiceName;

	private int additionalServiceDailyPrice;
}

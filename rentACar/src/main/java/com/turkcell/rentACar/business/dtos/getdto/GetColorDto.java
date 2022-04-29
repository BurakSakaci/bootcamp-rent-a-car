package com.turkcell.rentACar.business.dtos.getdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetColorDto {
	private int id;
	private String colorName;
}

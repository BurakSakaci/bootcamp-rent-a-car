package com.turkcell.rentACar.business.dtos.listdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandListDto {
	//sadece id'e göre isim istediği için
	private int id;
	private String brandName;
	
}

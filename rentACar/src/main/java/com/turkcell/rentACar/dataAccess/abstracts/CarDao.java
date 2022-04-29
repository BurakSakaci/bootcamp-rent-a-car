package com.turkcell.rentACar.dataAccess.abstracts;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.business.dtos.getdto.GetCarDto;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.concrates.Car;
@Repository
public interface CarDao extends JpaRepository<Car, Integer> {
	//boolean existsCarByCarId(int id);
	//boolean existsByBrand_BrandId(int brandId);
	//boolean existsByColor_ColorId(int colorId);
	List<Car> findByDailyPriceLessThan(int dailyPrice);
	List<Car> findByDailyPriceGreaterThan(int dailyPrice);
	List<Car> findByDailyPriceBetween(int minValue, int maxValue);
	List<Car> findByBrand_BrandId(int brandId);
	List<Car> findByColor_ColorId(int colorId);
	List<Car> findByBrand_BrandIdAndColor_ColorId(int brandId, int colorId);
}




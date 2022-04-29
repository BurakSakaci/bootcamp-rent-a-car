package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACar.entities.concrates.City;

public interface CityDao extends JpaRepository<City, Integer>{
	boolean existsByCityName(String cityName);
}

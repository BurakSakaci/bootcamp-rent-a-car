package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concrates.Brand;

@Repository
public interface BrandDao extends JpaRepository<Brand, Integer>{
	boolean existsByBrandName(String brandName);
}

package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concrates.Rent;
@Repository
public interface RentDao extends JpaRepository<Rent, Integer>{
	boolean existsByCar_CarId(int carId);
	List<Rent> findByCar_CarId(int carId);
	//List<Rent> findByFinishDateIsNull();
	
}

package com.turkcell.rentACar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concrates.CarMaintenance;
@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer>{
	//boolean existsCarMaintenanceByCarMaintenanceId(int id);
	//boolean existsByCar_CarId(int carId);
	//boolean existsByDescription(String description);
	//boolean existsByReturnDate(LocalDate returnDate);
	List<CarMaintenance> findByReturnDateBetween(LocalDate startDate, LocalDate returnDate);
	List<CarMaintenance> findByCar_CarId(int carId);
	List<CarMaintenance> findByReturnDateIsNull();
}

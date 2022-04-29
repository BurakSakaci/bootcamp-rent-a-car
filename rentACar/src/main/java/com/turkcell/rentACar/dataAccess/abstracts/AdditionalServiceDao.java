package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concrates.AdditionalService;
@Repository
public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer>{
	boolean existsByAdditionalServiceName(String AdditionalServiceName);
}

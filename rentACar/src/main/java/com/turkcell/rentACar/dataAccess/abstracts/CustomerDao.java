package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACar.entities.concrates.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

}

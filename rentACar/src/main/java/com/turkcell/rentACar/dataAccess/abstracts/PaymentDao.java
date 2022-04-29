package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concrates.Payment;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer>{

}

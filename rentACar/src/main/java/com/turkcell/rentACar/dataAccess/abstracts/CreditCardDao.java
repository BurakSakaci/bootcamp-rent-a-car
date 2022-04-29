package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concrates.CreditCard;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Integer>{
	List<CreditCard> findByCustomer_UserId(int customerId);
	boolean existsByCvvAndCardNo(int cvv, String cardNo);
}

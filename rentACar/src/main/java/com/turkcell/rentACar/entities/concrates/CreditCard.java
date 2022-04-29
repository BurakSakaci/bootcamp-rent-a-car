package com.turkcell.rentACar.entities.concrates;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_cards")
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_id")
	private int cardId;
	
	@Column(name = "card_no")
	private String cardNo;

	@Column(name = "card_holder")
	private String cardHolder;

	@Column(name = "expiration_month")
	private int expirationMonth;

	@Column(name = "expiration_year")
	private int expirationYear;

	@Column(name = "cvv")
	private int cvv;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}

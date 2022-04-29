package com.turkcell.rentACar.entities.concrates;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int invoiceId;
	
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "creation_date")
	private LocalDate creationDate;
	
	@Column(name = "from_date")
	private LocalDate fromDate;
	
	@Column(name = "to_date")
	private LocalDate toDate;
	
	@Column(name = "rental_day")
	private int rentalDay;
	
	@Column(name = "extra_amount")
	private int extraAmount=0;
	
	@Column(name = "total_amount")
	private int totalAmount;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "rent_id")
	private Rent rent;
	
	@OneToOne(mappedBy = "invoice")
	private Payment payment;
	
	
}

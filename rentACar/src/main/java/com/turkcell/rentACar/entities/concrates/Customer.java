package com.turkcell.rentACar.entities.concrates;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.turkcell.rentACar.core.entities.concrates.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "Lazy"})
@PrimaryKeyJoinColumn(name="customer_id",referencedColumnName = "user_id")
public class Customer extends User{
	
	@Column(name = "registered_date")
	private LocalDate registeredDate;
	
	@OneToMany(mappedBy = "customer")
	private List<Rent> rents;
	
	@OneToMany(mappedBy = "customer")
	private List<Invoice> invoices;
	
	@OneToMany(mappedBy = "customer")
	private List<Payment> payments;
	
	@OneToMany(mappedBy = "customer")
	private List<CreditCard> creditCards;
	
	
}

package com.turkcell.rentACar.entities.concrates;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rents")
public class Rent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rent_id")
	private int rentId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rented_city_id")
	private City rentedCity;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "delivered_city_id")
	private City deliveredCity;
	
	@Column(name = "starting_kilometer")
	private int startingKilometer;

	@Column(name = "ending_kilometer")
	private int endingKilometer;
	
	@Column(name= "start_date")
	private LocalDate startDate;
	
	@Column(name= "finish_date")
	private LocalDate finishDate;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
	
	@OneToOne
	@JoinColumn(name = "ordered_additional_service_id")
	private OrderedAdditionalService orderedAdditionalService;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer customer;	
	
	@OneToMany(mappedBy = "rent")
	private List<Invoice> invoices;	
	
	@OneToMany(mappedBy = "rent")
	private List<Payment> payments;
	
	
}
//Cities tablosu

package com.turkcell.rentACar.entities.concrates;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cities")
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "city_id")
	private int cityId;

	@Column(name = "city_name")
	private String cityName;

	@OneToMany(mappedBy = "rentedCity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Rent> rentalCarRented;

	@OneToMany(mappedBy = "deliveredCity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Rent> rentalCarDropOff;
}

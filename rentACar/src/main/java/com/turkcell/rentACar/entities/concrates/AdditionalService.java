package com.turkcell.rentACar.entities.concrates;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "additional_services")
public class AdditionalService {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "additional_service_id")
	private int additionalServiceId;
	
	@Column(name = "additional_service_name")
	private String additionalServiceName;
	
	@Column(name = "additional_service_dailyPrice")
	private int additionalServiceDailyPrice;
	
	/*
	@ManyToMany(mappedBy = "additionalServices", fetch = FetchType.LAZY)
	private Set<OrderedAdditionalService> orderedAdditionalServices;
	*/

}

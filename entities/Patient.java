package com.app.entities;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("patients")
@PrimaryKeyJoinColumn(name = "patient_id")
@Table(name="patients")
public class Patient extends User {
	    
		@Enumerated(EnumType.STRING)
		private BloodGroup bloodGroup;

		@Enumerated(EnumType.STRING)
		private HealthPlan healthPlan;
		
		private LocalDate planExpiryDate;
		
}

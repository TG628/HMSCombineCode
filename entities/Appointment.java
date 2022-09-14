package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "appointments")
@NoArgsConstructor
@Getter
@Setter
public class Appointment extends BaseEntity{
	
	@ManyToOne(fetch = FetchType.LAZY)
	//One patient can have multiple appointments 
	@JoinColumn(name = "patient_id")
	private Patient patientId;
	@Column
	private LocalDate date;
	@Column
	@Enumerated(EnumType.STRING)
	private TimeSlots timeSlot;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Staff doctor;
	@Column
	private String report;
	
}

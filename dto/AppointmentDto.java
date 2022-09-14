package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.app.entities.TimeSlots;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AppointmentDto {

	@NotNull
	private LocalDate date;
	@NotNull
	private TimeSlots timeSlot;

	@JsonProperty(access = Access.READ_ONLY)
	private String report;

}

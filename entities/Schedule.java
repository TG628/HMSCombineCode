package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule extends BaseEntity {
		
		@Column
		@Enumerated(EnumType.STRING)
		private Day day;
		@Column
		@Enumerated(EnumType.STRING)
		private Shift shift;
		
		@ManyToOne
		private Staff staff;
				
	}

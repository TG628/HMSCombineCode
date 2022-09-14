package com.app.entities;

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
@DiscriminatorValue(value = "staffs")
@PrimaryKeyJoinColumn(name = "staff_id")
@Table(name="staffs")
public class Staff extends User {
	
			@Enumerated(EnumType.STRING)
			private Education education;
			private String degreeCertificate;
			@Enumerated(EnumType.STRING)
			private Category category;
			@Enumerated(EnumType.STRING)
			private Shift shift;
}	

package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "application")
public class Application extends BaseEntity {
	private String name;
	@Column(unique = true,nullable = false)
	private String email;
	@Enumerated(EnumType.STRING)
	private Category post;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String resumePath;
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;

}



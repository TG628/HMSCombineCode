package com.app.dto;

import com.app.entities.ApplicationStatus;
import com.app.entities.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApplicationDto {
	private String name;
	private String email;
	private Category post;
	@JsonProperty(access = Access.READ_ONLY)
	private ApplicationStatus status;
}

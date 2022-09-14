package com.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

	@Email
	@NotBlank(message = "Email Address cannot be blank")
	private String email;
	
	
	//To avoid serialization of password, password cannot be sent in response
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
}

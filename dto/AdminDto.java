package com.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.app.entities.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class AdminDto {
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	@Email
	@NotBlank(message = "Email cannot be blank")
	private String email;
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,15}$"
//			,message = "password must contain at least one digit,uppercase ,lowercase and special character")
	private String password;
	@NotBlank(message = "Role cannot be blank")
	
	private Role role;

}

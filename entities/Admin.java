package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "admin")
public class Admin extends BaseEntity {

	@Column(name = "full_name",length = 50)
	private String name;
	@Column(name = "email",length = 50,unique = true)
	private String email;
	//@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,15}$"
	//		,message = "password must contain at least one digit,uppercase ,lowercase and special character")
	@Column(length=100,nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
		
}

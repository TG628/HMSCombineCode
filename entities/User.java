package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user")
@Table(name = "users")
public class User extends BaseEntity {
	@Column(name = "first_name", length = 20)
	private String firstName;
	@Column(name = "last_name", length = 20)
	private String lastName;
	@Column(length = 30)
	private String phone;
	@Column(length = 30, unique = true,nullable = false)
	private String email;
	@Column(length = 20, nullable = false)
	//This is to be added in dto:- Minimum eight, max 15 characters, at least one uppercase letter, one lowercase letter, one number and one special character
	//@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%#*?&])[A-Za-z\\d@$!%*?&]{8,15}$",message = "Password must be atleast 8 ch and max 15 ch and must contain 1 upper case, 1 lower case letter,1 special character")
	private String password;
	@Column(name="user_image",length=200)
	private String imagePath;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Column(name="dob")
	private LocalDate dob;
	@Column(name="adhar_number",length = 12)
	private String adharNo;
	
}

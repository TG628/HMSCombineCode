package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address_table")
@Getter
@Setter
@NoArgsConstructor
public class Address extends BaseEntity {
	@Column(length = 50, name = "address_Line1")
	private String addressLine1;
	@Column(length = 50, name = "address_Line2")
	private String addressLine2;
	@Column(length = 20)
	private String city;
	@Column(length = 20)
	private String state;
	@Column(length = 20)
	private String country;
	@Column(length = 10,name="pin_code")
	private String pinCode;
	// one to one uni dir asso between User <----- Address
	// parent side : User , child : address
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@MapsId // To tell hibernate , Address wil NOT have a separate PK , it will be shared
			// with users table n it will acts as FK referencing PK of the users table
	private User user;
}

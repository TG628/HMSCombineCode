package com.app.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AddressDto {
	
	@NotBlank(message = "Addres Line 1 cannot be blank")
	private String addressLine1;
	
	private String addressLine2;
	@NotBlank(message = "City cannot be blank")
	private String city;
	@NotBlank(message = "State cannot be blank")
	private String state;
	@NotBlank(message = "Country cannot be blank")
	private String country;
	@NotBlank(message = "Pin Code cannot be blank")
	private String pinCode;
	

}

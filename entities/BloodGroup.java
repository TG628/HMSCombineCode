package com.app.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BloodGroup {
	O_POSITIVE("O+"),O_NEGATIVE("O-"),
	A_POSITIVE("A+"),A_NEGATIVE("A-"),
	B_POSITIVE("B+"),B_NEGATIVE("B-"),
	AB_POITIVE("AB+"),
	AB_NEGATIVE("AB-");
	@JsonValue  
	private final String type;

	BloodGroup(String type) {
		this.type=type;
	}

	@Override
	public String toString() {
		
		return type;
	}
	
}

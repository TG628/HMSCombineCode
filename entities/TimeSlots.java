package com.app.entities;

public enum TimeSlots {
	MORNING("9am to 12am"),AFTERNOON("1pm to 4pm"),EVENING("4pm to 7pm"),NIGHT("8pm to 11pm");
	
	private final String time;

	TimeSlots(String time) {
		this.time=time;
	}

	@Override
	public String toString() {
		
		return name()+"( "+time+" )";
	}
	
	
}

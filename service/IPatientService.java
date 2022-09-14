package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.AddressDto;
import com.app.dto.AppointmentDto;
import com.app.dto.PatientDto;
import com.app.entities.Appointment;
import com.app.entities.Patient;

public interface IPatientService {

	public PatientDto addPatientDetails(PatientDto patient,AddressDto addressDto,MultipartFile imgFile) throws IOException;
	
	public PatientDto getPatientDetails(String email,String password);
	
	public PatientDto getPatientDetails(long patId);

	public PatientDto updatePatientDetails(Patient detachedPatient);
	
	public byte[] downloadReport(long aptId) throws IOException;

	public List<Appointment> getReportList(long patId); 
	Appointment bookAppointment(AppointmentDto newAppointment,long patId);
	
	String setPlan(long patId,String plan);
}

package com.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.AppointmentRepository;
import com.app.dao.PatientRepository;
import com.app.dto.AddressDto;
import com.app.dto.AppointmentDto;
import com.app.dto.PatientDto;
import com.app.entities.Appointment;
import com.app.entities.HealthPlan;
import com.app.entities.Patient;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PatientServiceImpl implements IPatientService {

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PatientRepository patRepo;
	@Autowired
	private AppointmentRepository aptRepo;
	@Autowired
	private ImageHandlingService imgService;
	@Autowired
	private UserServiceImpl userService;
	@Override
	public PatientDto addPatientDetails(PatientDto patientDto,AddressDto addressDto,MultipartFile imgFile) throws IOException {
		
		Patient patient= patRepo.save(mapper.map(patientDto, Patient.class));
		userService.saveAddress(patient.getId(), addressDto);
		if(imgFile!=null) {
			imgService.saveImage(patient.getId(), imgFile);
		}
		
		return mapper.map(patient, PatientDto.class);
	}

	@Override
	public PatientDto getPatientDetails(String email, String password) {
		Patient patient = patRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Email Id " + email));
		if (patient.getPassword().equals(password))
			
			return mapper.map(patient, PatientDto.class);
		else
			throw new RuntimeException("invalid password");

	}

	@Override
	public PatientDto getPatientDetails(long patId) {

		Patient patient = patRepo.findById(patId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Patient ID " + patId));
		return mapper.map(patient, PatientDto.class);
	}

	@Override
	public PatientDto updatePatientDetails(Patient detachedPatient) {

		if (patRepo.existsById(detachedPatient.getId())) {
			Patient patient = patRepo.save(detachedPatient);
			return mapper.map(patient, PatientDto.class);// update
		}
			
		throw new ResourceNotFoundException("Invalid Emp ID : Updation Failed !!!!!!!!!" + detachedPatient.getId());
	}

	@Override
	public byte[] downloadReport(long aptId) throws IOException {

		// get appointment object by appointment id
		Appointment appointment = aptRepo.findById(aptId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid appointment id"));

		// get the path of report file from data base
		final String path = appointment.getReport();
		// convert the file into byte[] by using Files class method readAllBytes()

		return Files.readAllBytes(Paths.get(path));
	}

	@Override
	public List<Appointment> getReportList(long patId) {
		Patient patient = patRepo.findById(patId).orElseThrow();
		List<Appointment> reportList = aptRepo.findByPatientId(patient);
		System.out.println(reportList);
		if(reportList==null) {
			throw new ResourceNotFoundException("Invalid patient id");
		}
		return reportList;
	}
	
	@Override
	public Appointment bookAppointment(AppointmentDto newAppointment,long patId) {
		Appointment appointment=mapper.map(newAppointment,Appointment.class);
		Patient patient = patRepo.findById(patId).orElseThrow(() -> new ResourceNotFoundException("Invalid Patient ID " + patId));
		appointment.setPatientId(patient);
		return aptRepo.save(appointment);
	}
	
	@Override
	public String setPlan(long patId, String plan) {
		
		Patient patient = patRepo.findById(patId).orElseThrow(() -> new ResourceNotFoundException("Invalid Patient ID " + patId));
		System.out.println("plan in service: "+HealthPlan.valueOf(plan.toUpperCase()));
		patient.setHealthPlan(HealthPlan.valueOf(plan.toUpperCase()));
		patient.setPlanExpiryDate(LocalDate.now().plusYears(1));
		return "Plan added Sucessfully!!";
	}

}

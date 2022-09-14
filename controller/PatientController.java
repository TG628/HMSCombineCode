package com.app.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.AddressDto;
import com.app.dto.ApiResponse;
import com.app.dto.AppointmentDto;
import com.app.dto.LoginDto;
import com.app.dto.PatientDto;
import com.app.entities.Patient;
import com.app.service.IPatientService;
import com.app.service.ImageHandlingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/patient")
@Slf4j
public class PatientController {

	@Autowired
	private IPatientService patientService;
	
	@Autowired
	private ImageHandlingService imageService;

	// register patient here
	@PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> addPatientDetails(@RequestPart PatientDto patientDto, @RequestPart AddressDto addressDto,
			@RequestPart(required = false) MultipartFile imgFile) throws IOException {

		PatientDto patient = patientService.addPatientDetails(patientDto, addressDto, imgFile);
		return new ResponseEntity<>(patient, HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<?> patientLogin(@RequestBody LoginDto loginDetails) {
		try {
			System.out.println("email: " + loginDetails.getEmail() + " password :" + loginDetails.getPassword());
			return ResponseEntity
					.ok(patientService.getPatientDetails(loginDetails.getEmail(), loginDetails.getPassword()));
		} catch (RuntimeException e) {
			System.out.println("err in get  emp " + e);
			return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.NOT_FOUND);// => // id
		}
	}

	@GetMapping("/{patId}")
	public ResponseEntity<?> getPatientDetails(@PathVariable long patId) {
		System.out.println("in get patient " + patId);
		try {
			return ResponseEntity.ok(patientService.getPatientDetails(patId));
		} catch (RuntimeException e) {
			System.out.println("err in get  emp " + e);
			return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.NOT_FOUND);// => // id
		}
	}

	// add REST API endpoint to update existing patient details
	@PutMapping("/update")
	public ResponseEntity<?> updatePatientDetails(@RequestBody Patient detachedPat) {

		System.out.println("in update emp " + detachedPat);
		try {
			return ResponseEntity.ok(patientService.updatePatientDetails(detachedPat));
		} catch (RuntimeException e) {
			System.out.println("err in update  patient " + e);
			return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("{patId}/reports")
	public ResponseEntity<?> getReportList(@PathVariable long patId) {
		try {
			return ResponseEntity.ok(patientService.getReportList(patId));
		} catch (RuntimeException e) {
			return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/report/{aptId}", produces = { MediaType.APPLICATION_PDF_VALUE })
	public ResponseEntity<?> downloadReport(@PathVariable long aptId) throws IOException {

		return ResponseEntity.ok(patientService.downloadReport(aptId));

	}

	@PostMapping("/bookAppointment/{patId}")
	public ResponseEntity<?> bookAppointment(@RequestBody @Valid AppointmentDto newAppointment,
			@PathVariable long patId) {
		System.out.println("in book appointment ");
		return ResponseEntity.ok(patientService.bookAppointment(newAppointment, patId));
	}

	@PatchMapping("subscribePlan/{patId}")
	public ResponseEntity<?> subscribePlan(@RequestBody String plan, @PathVariable long patId) {
		System.out.println("in get subscribePlan ");
		try {
			System.out.println("plan" + plan);
			return ResponseEntity.ok(patientService.setPlan(patId, plan));
		} catch (RuntimeException e) {

			return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.NOT_FOUND);// =>
																								// System.out.println("err
																								// in get
																								//  patient " + e);
		}
	}
	
	@GetMapping(value = "{userId}/image", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<?> getImage(@PathVariable long userId) throws IOException {

		return ResponseEntity.ok(imageService.restoreImage(userId));
	}
}

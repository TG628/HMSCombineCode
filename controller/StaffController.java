package com.app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.entities.Appointment;
import com.app.entities.Staff;
import com.app.service.IStaffService;
import com.app.service.ImageHandlingService;

@RestController
@RequestMapping("/staff")
public class StaffController {

	// dep : service layer i/f
	@Autowired
	private IStaffService staffService;
	@Autowired
	private ImageHandlingService imageService;

	// add REST API endpoint to ret list of all Appointments
	
	
	@GetMapping("/showAppointments")
	public List<Appointment> listAppointments() {
		System.out.println("in list appointments");
		return staffService.getAllAppointmentsDetails();
	}

	@PostMapping(value = "/uploadReport/{aptId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> uploadReport(@PathVariable long aptId, @RequestParam MultipartFile reportFile)
			throws IOException {

		return ResponseEntity.ok(staffService.saveReport(aptId, reportFile));
	}

	@PostMapping("/{userId}/uploadImage")
	public ResponseEntity<?> uploadImage(@RequestParam long userId, @RequestParam MultipartFile imgFile)
			throws IOException {
		Staff staff = staffService.getStaff(userId);
		imageService.saveImage(userId, imgFile);
		return ResponseEntity.ok(staff);
	}
	
	@GetMapping(value = "{userId}/image", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<?> getImage(@PathVariable long userId) throws IOException {

		return ResponseEntity.ok(imageService.restoreImage(userId));
	}


}

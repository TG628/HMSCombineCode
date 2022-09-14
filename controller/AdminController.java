package com.app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.AddressDto;
import com.app.dto.AdminDto;
import com.app.dto.ApiResponse;
import com.app.dto.StaffDto;
import com.app.dto.StaffScheduleDto;
import com.app.entities.Admin;
import com.app.entities.Staff;
import com.app.service.IAdminService;
import com.app.service.IStaffService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private IAdminService adminService;
	@Autowired
	private IStaffService staffService;
	

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AdminDto newAdmin) {
		System.out.println(newAdmin.getEmail());
		System.out.println(newAdmin.getPassword());
		Admin admin = adminService.getAdminByEmail(newAdmin.getEmail());
		if (admin.getPassword().equals(newAdmin.getPassword()))
			return ResponseEntity.ok(admin);
		return ResponseEntity.ok(new ApiResponse("Invalid Password"));

	}

	@PostMapping("/add")
	public ResponseEntity<?> addAdmin(@RequestBody AdminDto newAdmin) {
		System.out.println(newAdmin.getEmail());
		System.out.println(newAdmin.getPassword());
		return new ResponseEntity<>(adminService.addNewAdmin(newAdmin), HttpStatus.CREATED);
	}

	@PostMapping(value = "/addStaff", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> addStaff(@RequestPart StaffDto staffDto, @RequestPart AddressDto addressDto,
			@RequestPart(required = false) MultipartFile imgFile,
			@RequestPart(required = false) MultipartFile degreeFile) throws IOException {
		Staff detachedStaff = staffService.addStaff(staffDto, imgFile, addressDto, degreeFile);
		return new ResponseEntity<>(detachedStaff, HttpStatus.CREATED);
	}

	@GetMapping("/staffSchedule")
	public ResponseEntity<?> getStaffList() {
		return ResponseEntity.ok(adminService.getStaffList());
	}

	@PostMapping("/updateShifts")
	public ResponseEntity<?> updateStaffShits(@RequestBody List<StaffScheduleDto> list) {

		staffService.updateShifts(list);

		return ResponseEntity.ok(adminService.getStaffList());
	}

}

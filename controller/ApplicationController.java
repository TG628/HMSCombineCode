package com.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApiResponse;
import com.app.dto.ApplicationDto;
import com.app.service.IApplicationService;



@RestController
@RequestMapping("/careers")
public class ApplicationController {
	@Autowired
	private IApplicationService applnService;
	
	@PostMapping(value="/newApplication",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> newApplication(@RequestPart ApplicationDto applnDto,@RequestPart MultipartFile resumeFile) throws IOException{
		applnService.saveApplication(applnDto, resumeFile);
		return new ResponseEntity<>(new ApiResponse("Application saved!!"),HttpStatus.CREATED);
	}
	
	@GetMapping("/applicantsList")
	public ResponseEntity<?> getApplicantsList(){
		return ResponseEntity.ok(applnService.getAllApplications());
	}
	
	@GetMapping(value="/resume/{applnId}",produces = {MediaType.APPLICATION_PDF_VALUE})
	public ResponseEntity<?> downloadResume(@PathVariable long applnId) throws IOException{
		return ResponseEntity.ok(applnService.downloadResume(applnId));
	}
	
	@PostMapping("/updateStatus/{applnId}")
	public ResponseEntity<?> updateApplnStatus(@PathVariable long applnId){
		return ResponseEntity.ok(applnService.updateStatus(applnId));
	}
}

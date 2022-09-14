package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.AddressDto;
import com.app.dto.StaffDto;
import com.app.dto.StaffScheduleDto;
import com.app.entities.Appointment;
import com.app.entities.Staff;

public interface IStaffService {
	
	Staff addStaff(StaffDto staffDto,MultipartFile imgFile,AddressDto addressDto,MultipartFile degreeFile) throws IOException;
	Staff getStaff(long userId);
	String updateShifts(List<StaffScheduleDto> staffList);
	List<Appointment> getAllAppointmentsDetails();
	String saveReport(long patId,MultipartFile reportFile) throws IOException;
}

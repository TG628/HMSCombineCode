package com.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.AppointmentRepository;
import com.app.dao.StaffRepository;
import com.app.dto.AddressDto;
import com.app.dto.StaffDto;
import com.app.dto.StaffScheduleDto;
import com.app.entities.Appointment;
import com.app.entities.Shift;
import com.app.entities.Staff;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private StaffRepository staffRepo;
	@Autowired
	private IUserService userService;
	@Autowired
	private ImageHandlingService imgService;
	@Autowired
	private IDegreeDocHandlingService degreeCertificateService;
	@Value("${file.upload.reports}")
	private String folderLocation;
	
	@PostConstruct
	public void anyInit() {
		// chk if folder exists --if not create one !
		// java.io.File => represents abstract path to a file /folder
		File folder = new File(folderLocation);
		if (!folder.exists()) {
			folder.mkdirs();
			log.info("folder created....");
		} else
			log.info("folder alrdy exists !");
	} 
	@Override
	public Staff addStaff(StaffDto staffDto,MultipartFile imgFile,AddressDto addressDto,MultipartFile degreeFile) throws IOException {
		Staff transiemtStaff=mapper.map(staffDto, Staff.class);
		Staff staff = staffRepo.save(transiemtStaff);
		userService.saveAddress(staff.getId(), addressDto);
		if(imgFile!=null)
		imgService.saveImage(staff.getId(), imgFile);
		if(degreeFile!=null)
		degreeCertificateService.uploadDegreeCertificate(staff.getId(), degreeFile);
		return staff;
	}
	
	@Override
	public Staff getStaff(long userId) {
		
		Staff staff=staffRepo.findById(userId).orElseThrow();
		return staff;
	}

	@Override
	public String updateShifts(List<StaffScheduleDto> staffList) {
		staffList.forEach(s->updateShift(s.getId(),s.getShift()));
		return "Shifts updated";
	}
	public void updateShift(long staffId,Shift shift) {
		Staff staff=staffRepo.findById(staffId).orElseThrow();
		staff.setShift(shift);
		staffRepo.save(staff);
	}
	
	@Autowired
	private AppointmentRepository appointmentRepo;
	
	@Override
	public List<Appointment> getAllAppointmentsDetails() {
		return appointmentRepo.findAll();
		}

	@Override
	public String saveReport(long aptId, MultipartFile reportFile) throws IOException {
		//get appointment object by appointment id
		Appointment appointment = appointmentRepo.findById(aptId)
				.orElseThrow(()->new ResourceNotFoundException("Invalid Appointment id"));
		
		//Absolute path foe save report file to in database
		String path=folderLocation+File.separator+reportFile.getOriginalFilename()+aptId;
		
		//save the path in database 
		appointment.setReport(path);
		
		//copy the report file in server side folder.
		Files.copy(reportFile.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
		
		return "Report uploaded Succefully";
	}
}

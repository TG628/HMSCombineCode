package com.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AdminRepository;
import com.app.dao.StaffRepository;
import com.app.dto.AdminDto;
import com.app.dto.StaffScheduleDto;
import com.app.entities.Admin;
import com.app.entities.Staff;
@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

	@Autowired
	AdminRepository adminRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private StaffRepository staffRepo;
	@Override
	public Admin getAdminByEmail(String email) {
		Admin admin = adminRepo.findByEmail(email).orElseThrow(()->new RuntimeException("Invalid Email!!!"));
	
		// exception to be changed to 'UsernameNotFoundException' in spring security 
		return admin;
	}
	@Override
	public Admin addNewAdmin(AdminDto newAdmin) {
		Admin admin=mapper.map(newAdmin,Admin.class);
		System.out.println("Admin service, admin Id before saving transient admin "+admin.getId());
		adminRepo.save(admin);
		return null;
	}
	@Override
	public List<StaffScheduleDto> getStaffList() {
		List<Staff> staffList=staffRepo.findAll();
		List<StaffScheduleDto> staffDtoList=new ArrayList<StaffScheduleDto>();
		//staffList.stream().forEach(staff->staffDtoList.add(new StaffScheduleDto(staff.getId(),staff.getFirstName(),staff.getLastName(),staff.getCategory(),staff.getShift())));
		staffList.stream().forEach(staff->staffDtoList.add(mapper.map(staff, StaffScheduleDto.class)));
		
	return staffDtoList;
	}	

}

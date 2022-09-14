package com.app.service;

import java.util.List;

import com.app.dto.AdminDto;
import com.app.dto.StaffScheduleDto;
import com.app.entities.Admin;

public interface IAdminService {
	
	public Admin getAdminByEmail(String email);
	
	public Admin addNewAdmin(AdminDto newAdmin);
	
	public List<StaffScheduleDto> getStaffList();
	

	
}

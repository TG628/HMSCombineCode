package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApplicationDto;
import com.app.entities.Application;

public interface IApplicationService {
	
	public ApplicationDto saveApplication(ApplicationDto applnDto,MultipartFile resumeFile) throws IOException;
	
    public List<Application> getAllApplications();
	
	public String updateStatus(long applnId);
	
	public byte[] downloadResume(long applnId) throws IOException;
}

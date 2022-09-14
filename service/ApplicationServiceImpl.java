package com.app.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.ApplicationRepository;
import com.app.dto.ApplicationDto;
import com.app.entities.Application;
import com.app.entities.ApplicationStatus;

@Service
@Transactional
public class ApplicationServiceImpl implements IApplicationService {

	@Autowired
	private ApplicationRepository applnRepo;
	@Autowired 
	private ModelMapper mapper;
	@Autowired
	private ResumeDocHandlingServiceImpl resumeService;
	@Override
	public ApplicationDto saveApplication(ApplicationDto applnDto,MultipartFile resumeFile) throws IOException {
		Application appln=mapper.map(applnDto, Application.class);
		applnRepo.save(appln);
		resumeService.uploadResume(appln.getId(), resumeFile);
		return applnDto;
	}

	@Override
	public List<Application> getAllApplications() {
		
		return applnRepo.findAll();
	}

	@Override
	public String updateStatus(long applnId) {
		Application appln = applnRepo.findById(applnId).orElseThrow();
		appln.setStatus(ApplicationStatus.SEEN);
		applnRepo.save(appln);
		return "Application status changed";
	}

	@Override
	public byte[] downloadResume(long applnId) throws IOException {
		
		return resumeService.downloadResume(applnId);
	}

}

package com.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApplicationDto;

public interface IResumeDocHandlingService {

	public ApplicationDto uploadResume(long userId,MultipartFile resumeFile) throws IOException;
	public byte[] downloadResume(long userId) throws IOException;
	
}

package com.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.UserDto;

public interface ImageHandlingService {
	
	public UserDto saveImage(long userId,MultipartFile imgFile) throws IOException;
	public byte[] restoreImage(long userId) throws IOException;
	
}

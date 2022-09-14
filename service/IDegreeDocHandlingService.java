package com.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IDegreeDocHandlingService {
	public String uploadDegreeCertificate(long userId,MultipartFile degreeFile) throws IOException;
	public byte[] downloadDegreeCetificate(long userId) throws IOException;
}

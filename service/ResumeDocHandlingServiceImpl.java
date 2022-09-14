package com.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.ApplicationRepository;
import com.app.dto.ApplicationDto;
import com.app.entities.Application;
@Service
@Transactional
public class ResumeDocHandlingServiceImpl implements IResumeDocHandlingService {

	@Value("${file.upload.docs}") 
	private String folderLocation;
	@Autowired private 
	ModelMapper mapper;
	@Autowired
	private ApplicationRepository applicationRepo;
	@PostConstruct
	public void anyInit() {
		//chk if folder exists -- if not create one
		//java.io.File--> repersents abstract path to a file/folder
		File folder=new File(folderLocation);
		if(!folder.exists())
		{
			folder.mkdirs();
			System.out.println("folder created");
		
			
		}
		else
			System.out.println("folder exist already");
	}

	@Override
	public ApplicationDto uploadResume(long applnId, MultipartFile resumeFile) throws IOException {
		Application appln = applicationRepo.findById(applnId).orElseThrow();
		
		String path=folderLocation+File.separator+resumeFile.getOriginalFilename()+applnId;
		System.out.println("path "+path);
		appln.setResumePath(path);//update query upon commit
		//Copy uploaded multipart file to server side folder
		//java.nio.file.Files : copy(InputStream in , Path dest, CopyOptions.. ops)
		//Paths.get(String path) --->  Path
		applicationRepo.save(appln);
			Files.copy(resumeFile.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
	
		return mapper.map(appln, ApplicationDto.class);
		
	}

	@Override
	public byte[] downloadResume(long applnId) throws IOException {
		Application appln=applicationRepo.findById(applnId).orElseThrow();
		String path = appln.getResumePath();
		// java.nio.file.Files : public byte[] readAllBytes(Path path) throws IOExc
		if (path != null)
			return Files.readAllBytes(Paths.get(path));
		
		// => image is not yet assigned --throw exc to alert front end
		throw new RuntimeException("Resume not  found , for user " + appln.getName());

	}
	
}

package com.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.StaffRepository;
import com.app.entities.Staff;

import lombok.extern.slf4j.Slf4j;
@Service
@Transactional
@Slf4j
public class DegreeDocHandlingService implements IDegreeDocHandlingService {

	@Value("${file.upload.degrees}") 
	private String folderLocation;
	@Autowired
	private StaffRepository staffRepo;
	@PostConstruct
	public void anyInit() {
		//chk if folder exists -- if not create one
		//java.io.File--> repersents abstract path to a file/folder
		File folder=new File(folderLocation);
		if(!folder.exists())
		{
			folder.mkdirs();
			log.info("folder created");
		
			
		}
		else
			log.info("folder exist already");
	}

	@Override
	public String uploadDegreeCertificate(long userId, MultipartFile degreeFile) throws IOException {
		Staff staff=staffRepo.findById(userId).orElseThrow();
		String path=folderLocation+File.separator+degreeFile.getOriginalFilename()+userId;
		System.out.println("path "+path);
		staff.setDegreeCertificate(path);//update query upon commit
		//Copy uploaded multipart file to server side folder
		//java.nio.file.Files : copy(InputStream in , Path dest, CopyOptions.. ops)
		//Paths.get(String path) --->  Path
		staffRepo.save(staff);
			Files.copy(degreeFile.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
	
		return "Degree Certificate Saved !!!!";
	}

	@Override
	public byte[] downloadDegreeCetificate(long userId) throws IOException {
		Staff staff=staffRepo.findById(userId).orElseThrow();
		String path = staff.getDegreeCertificate();
		// java.nio.file.Files : public byte[] readAllBytes(Path path) throws IOExc
		if (path != null)
			return Files.readAllBytes(Paths.get(path));
		
		// => image is not yet assigned --throw exc to alert front end
		throw new RuntimeException("Degree Doc not  availbale , for user " + staff.getFirstName());

	}

}

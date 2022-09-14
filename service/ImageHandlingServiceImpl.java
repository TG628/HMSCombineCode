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

import com.app.dao.UserRepository;
import com.app.dto.UserDto;
import com.app.entities.User;
@Service
@Transactional

public class ImageHandlingServiceImpl implements ImageHandlingService {
	
	@Value("${file.user.uploads}")
	private String folderLocation;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepository userRepo;
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
	public UserDto saveImage(long userId, MultipartFile imgFile) throws IOException {
		User user=userRepo.findById(userId).orElseThrow();
		String path=folderLocation+File.separator+"user"+userId+imgFile.getOriginalFilename();
		System.out.println("path "+path);
		user.setImagePath(path);//update query upon commit
		//Copy uploaded multipart file to server side folder
		//java.nio.file.Files : copy(InputStream in , Path dest, CopyOptions.. ops)
		//Paths.get(String path) --->  Path
		userRepo.save(user);
			Files.copy(imgFile.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
	
		return mapper.map(user, UserDto.class);
		
		
	}

	@Override
	public byte[] restoreImage(long userId) throws IOException {
		// TODO Auto-generated method stub
		User user=userRepo.findById(userId).orElseThrow();
		String path = user.getImagePath();
		// java.nio.file.Files : public byte[] readAllBytes(Path path) throws IOExc
		if (path != null)
			return Files.readAllBytes(Paths.get(path));
		
		// => image is not yet assigned --throw exc to alert front end
		throw new RuntimeException("Image not  yet assigned , for user " + user.getFirstName());
	}
	
	/* public EmployeeDto saveImage(long empId, MultipartFile imgFile) throws IOException{
		// get emp dtls from emp id
		Employee emp = empRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Emp ID : Can't save image !!!!!!!"));
		//=>valid emp id , set image path in db
		//create absolute path to the image file n save it in DB
		String path=folderLocation+File.separator+imgFile.getOriginalFilename();
		log.info("path {}",path);
		emp.setImgPath(path);//update query upon commit
		//copy uploaded multipart file to server side folder
		//java.nio.file.Files : copy(InputStream in , Path dest, CopyOptions.. ops)
		//Paths.get(String path) --->  Path
		
			Files.copy(imgFile.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
	
		return mapper.map(emp, EmployeeDto.class);
	}
	
	@Override
	public byte[] restoreImage(long empId) throws IOException {
		// get emp dtls from emp id
		Employee emp = empRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Emp ID : Can't save image !!!!!!!"));
		// =>valid emp id ,get image path from db
		String path = emp.getImgPath();// OR in case of img stored in DB : emp.getImage() ---> byte[]
		// java.nio.file.Files : public byte[] readAllBytes(Path path) throws IOExc
		if (path != null)
			return Files.readAllBytes(Paths.get(path));
		// => image is not yet assigned --throw exc to alert front end
		throw new ResourceNotFoundException("Image not  yet assigned , for emp " + emp.getLastName());
	}
 */
}

package com.app.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AddressRepository;
import com.app.dao.UserRepository;
import com.app.dto.AddressDto;
import com.app.entities.Address;
import com.app.entities.User;
@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public String saveAddress(long userId, AddressDto addressDto) {
		User user=userRepo.findById(userId).orElseThrow();
		Address addr=mapper.map(addressDto,Address.class);
		addr.setUser(user);
		addressRepo.save(addr);
		return "Address Saved Scussefully!!";	
	}
	
	

}

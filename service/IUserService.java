package com.app.service;

import com.app.dto.AddressDto;

public interface IUserService {

	 String saveAddress(long userId,AddressDto addressDto);
	 
}

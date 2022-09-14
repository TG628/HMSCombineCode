package com.app;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HMS_11_09Application {
	public static void main(String[] args) {
		SpringApplication.run(HMS_11_09Application.class, args);
	}
	
	@Bean
	public ModelMapper mapper()
	{
	 ModelMapper modelMapper = new ModelMapper();
	 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	 return modelMapper;
	}
	
	
}

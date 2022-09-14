package com.app.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;


import com.app.entities.Application;

public interface ApplicationRepository  extends JpaRepository<Application, Long> {
	List<Application> findAll();
}

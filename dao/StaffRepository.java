package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

}

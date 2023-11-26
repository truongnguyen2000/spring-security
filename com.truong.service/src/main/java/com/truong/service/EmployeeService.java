package com.truong.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truong.entity.Employee;

public interface EmployeeService {

	Employee findOne(int id);
	
	Employee findByUsername(String username);
	
	List<Employee> findAll() throws SQLException;
}

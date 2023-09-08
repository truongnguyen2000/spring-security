package com.truong.dao;

import java.util.List;

import com.truong.entity.Employee;

public interface EmployeeDao {
	
	Employee findOne(int id);
	
	Employee findByUsername(String username);
	
	List<Employee> findAll();
}

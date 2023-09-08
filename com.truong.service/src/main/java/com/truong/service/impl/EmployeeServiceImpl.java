package com.truong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truong.dao.EmployeeDao;
import com.truong.entity.Employee;
import com.truong.service.EmployeeService;

@Transactional(rollbackFor = Exception.class)
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeDao dao;
	
	@Override
	@Transactional(readOnly = true)
	public Employee findOne(int id) {
		return dao.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Employee findByUsername(String username) {
		return dao.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAll() {
		return dao.findAll();
	}

}

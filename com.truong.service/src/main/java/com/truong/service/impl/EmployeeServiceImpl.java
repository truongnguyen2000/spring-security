package com.truong.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
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
	
	@Autowired
	DataSource dataSource;
	
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
	public List<Employee> findAll() throws SQLException {
		Connection connection = dataSource.getConnection();

		try {
		    String currentDataSourceUrl = connection.getMetaData().getURL();
		    System.out.print("API Request - Current DataSource URL: " + currentDataSourceUrl);
		} catch (Exception e) {
		    // Xử lý exception nếu cần
		} finally {
		    DataSourceUtils.releaseConnection(connection, dataSource);
		}


		
		
		return dao.findAll();
	}

}

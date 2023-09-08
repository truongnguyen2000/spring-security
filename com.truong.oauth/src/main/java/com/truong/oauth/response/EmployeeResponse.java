package com.truong.oauth.response;

import java.util.List;
import java.util.stream.Collectors;

import com.truong.entity.Employee;

public class EmployeeResponse {

	private int id;
	
	private String username;
	
	private String authorities;

	public EmployeeResponse() {}
	
	public EmployeeResponse(Employee employee) {
		this.id = employee.getId();
		this.username = employee.getUsername();
		this.authorities = employee.getAuthorities();
	}
	
	public List<EmployeeResponse> mapToList(List<Employee> employees) {
		return employees.stream().map(EmployeeResponse::new).collect(Collectors.toList());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	
	
}

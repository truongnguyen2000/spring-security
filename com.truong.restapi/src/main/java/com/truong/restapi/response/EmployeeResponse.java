package com.truong.restapi.response;

import java.util.List;
import java.util.stream.Collectors;

import com.truong.entity.AuthoritiesJson;
import com.truong.entity.Employee;

public class EmployeeResponse {

	private int id;
	
	private String username;
	
	private List<AuthoritiesJson> authorities;

	public EmployeeResponse() {}
	
	public EmployeeResponse(Employee employee) throws Exception {
		this.id = employee.getId();
		this.username = employee.getUsername();
		this.authorities = employee.getListAuthorities();
	}
	
	public List<EmployeeResponse> mapToList(List<Employee> employees) {
		return employees.stream().map(t -> {
			try {
				return new EmployeeResponse(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
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

	public List<AuthoritiesJson> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthoritiesJson> authorities) {
		this.authorities = authorities;
	}
	
	
}

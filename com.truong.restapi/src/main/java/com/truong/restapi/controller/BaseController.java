package com.truong.restapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.Strings;
import com.truong.common.exception.CustomException;
import com.truong.entity.Employee;
import com.truong.entity.MediiUser;
import com.truong.service.EmployeeService;


/**	
 * @author Truong
 *
 * 3 Oct 2023
 */
public class BaseController {
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	EmployeeService employeeService;
	
	public Employee getUser() throws CustomException {
		
		String token = this.getRequestHeaderAccessToken();
		
		if (Strings.isNullOrEmpty(token)) {
			throw new CustomException(HttpStatus.UNAUTHORIZED,  HttpStatus.UNAUTHORIZED.name());
		}
		
		OAuth2Authentication oauth2 = tokenStore.readAuthentication(token);
		MediiUser user = (MediiUser) oauth2.getPrincipal();
		
		Employee employee = employeeService.findOne(user.getId());
		
		if (employee == null) {
			throw new CustomException(HttpStatus.UNAUTHORIZED,  HttpStatus.UNAUTHORIZED.name());
		}
		
		return employee;
	}
	
	public String getRequestHeaderAccessToken() {
		return this.getRequest().getHeader("Authorization").replace("Bearer ", "");
	}
	
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
}

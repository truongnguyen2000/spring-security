package com.truong.restapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.Strings;
import com.truong.common.exception.CustomException;
import com.truong.common.utils.StringValue;
import com.truong.entity.Employee;
import com.truong.entity.CustomUser;
import com.truong.service.EmployeeService;

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
		CustomUser user = (CustomUser) oauth2.getPrincipal();
		
		Employee employee = employeeService.findOne(user.getId());
		
		if (employee == null) {
			throw new CustomException(HttpStatus.UNAUTHORIZED,  HttpStatus.UNAUTHORIZED.name());
		}
		
		return employee;
	}
	
	public String getRequestHeaderAccessToken() {
	    HttpServletRequest request = this.getRequest();
	    if (request != null) {
	        String authorizationHeader = request.getHeader("Authorization");
	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            return authorizationHeader.replace("Bearer ", "");
	        }
	    }
	    return null;
	}

	public HttpServletRequest getRequest() {
	    RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
	    if (attributes instanceof ServletRequestAttributes) {
	        return ((ServletRequestAttributes) attributes).getRequest();
	    }
	    return null;
	}
	
	public List<String> getAuthority() throws JsonParseException, JsonMappingException, IOException {
		return tokenStore.readAuthentication(this.getRequest().getHeader("Authorization").replace("Bearer ", ""))
				.getAuthorities().stream().map(x -> x.getAuthority()).filter(x -> !x.contains("ROLE_"))
				.collect(Collectors.toList());

	}
	
	public boolean isOwner() throws Exception {
		return this.getAuthority().stream().anyMatch(x -> x.equals(StringValue.OWNER));
	}

}

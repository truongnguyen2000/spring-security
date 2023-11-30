package com.truong.oauth.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.truong.entity.Employee;
import com.truong.entity.CustomUser;
import com.truong.service.EmployeeService;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	EmployeeService employeeService;


    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        Employee employee = employeeService.findByUsername(username);
        
        if (employee == null) {
			throw new UsernameNotFoundException(String.format("The user doesn't exist"));
		}
        
        List<GrantedAuthority> authorities = new ArrayList<>();
		try {
			authorities = employee.getListAuthorities()
					.stream().map(x -> new SimpleGrantedAuthority(x.getCode())).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}

                
        CustomUser userDetails = new CustomUser(username, employee.getPassword(), (int) employee.getId(), "",
				"", authorities);
        
        return userDetails;
    }
}

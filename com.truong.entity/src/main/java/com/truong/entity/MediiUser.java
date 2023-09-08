package com.truong.entity;

import java.util.Collection;

import org.springframework.security.core.userdetails.User;

public class MediiUser extends User {

	private static final long serialVersionUID = -3531439484732724601L;

	private int id;
	private String phone;
	private String email;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MediiUser(String username, String password, int id, String phone, String email, Collection authorities) {
		super(username, password, true, true, true, true, authorities);

		this.setId(id);
		this.phone = phone;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

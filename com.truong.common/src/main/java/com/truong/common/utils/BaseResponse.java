package com.truong.common.utils;

import org.springframework.http.HttpStatus;

public class BaseResponse<T> {
	
	private int status;
	
	private String message;
	 
	private T data;
	
	public BaseResponse() {
		this.status = HttpStatus.OK.value();
		this.message = HttpStatus.OK.name();
		this.data = null;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(HttpStatus httpStatus) {
		this.status = httpStatus.value();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(HttpStatus httpStatus) {
		this.message = httpStatus.name();
	}
	
	public void setMessageError(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}

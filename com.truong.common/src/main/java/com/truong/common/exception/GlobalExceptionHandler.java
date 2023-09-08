package com.truong.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.truong.common.utils.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
    	
    	BaseResponse<Object> response = new BaseResponse<>();
    	
    	response.setStatus(ex.getHttpStatus());
    	response.setMessageError(ex.getMessage());
    	
    	return new ResponseEntity<>(response, HttpStatus.OK); 
    }
}


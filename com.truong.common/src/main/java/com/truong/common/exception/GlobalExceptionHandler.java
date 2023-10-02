package com.truong.common.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.truong.common.utils.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
    	
    	BaseResponse<Object> response = new BaseResponse<>();
    	
    	response.setStatus(ex.getHttpStatus());
    	response.setMessageError(ex.getMessage());
    	
    	return new ResponseEntity<>(response, HttpStatus.OK); 
    }
    

    @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, 
			HttpHeaders headers, 
			HttpStatus status, 
			WebRequest request) {
		BaseResponse<Object> response = new BaseResponse<>();
		
	    List<String> errors = new ArrayList<String>();

	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getDefaultMessage());
	    }
	  
	    response.setStatus(HttpStatus.BAD_REQUEST);
		// response.setMessageError(Utils.convertListObjectToJsonArray(errors));
	    response.setMessageError(errors.stream().collect(Collectors.joining(",")));
		
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}


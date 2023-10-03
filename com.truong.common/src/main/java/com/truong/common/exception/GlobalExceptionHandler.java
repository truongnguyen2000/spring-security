package com.truong.common.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.truong.common.utils.BaseResponse;
import com.truong.common.utils.Utils;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {

		BaseResponse<Object> response = new BaseResponse<>();

		response.setStatus(ex.getHttpStatus());
		response.setMessageError(ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
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

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BaseResponse<Object> response = new BaseResponse<>();
		response.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
		response.setMessageError(String.format("Method not support :)), %s", ex.getSupportedHttpMethods().toString()));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BaseResponse<Object> response = new BaseResponse<>();

		String message = ex.getParameterName() + " parameter is missing";

		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setMessageError(message);

		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		BaseResponse<Object> response = new BaseResponse<>();
		if (ex instanceof CustomException) {
			CustomException customException = (CustomException) ex;
			response.setStatus(customException.getHttpStatus());
			response.setMessageError(customException.getErrorMessage());
			response.setData(customException.getData());
	    } else if (ex instanceof InvalidTokenException) {
	    	response.setStatus(HttpStatus.UNAUTHORIZED);
	    	response.setMessageError("Không có quyền truy cập hệ thống");
		} else if (ex instanceof AccessDeniedException) {
	    	response.setStatus(HttpStatus.FORBIDDEN);
	    	response.setMessageError("Tài khoản bạn không có quyền truy cập tính năng này");
		} else {
	    	response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	    	response.setMessageError(ex.getLocalizedMessage());
	    	
	    	StringBuilder stringBuilder = new StringBuilder();
	    	stringBuilder.append(Utils.getDatetimeString(new Date()));
	    	stringBuilder.append(": ERROR====>");
	    	System.out.println(stringBuilder);
	    	
	    	ex.printStackTrace();
	    }
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
}

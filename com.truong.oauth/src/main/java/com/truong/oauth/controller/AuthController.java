package com.truong.oauth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.truong.common.exception.CustomException;
import com.truong.common.utils.BaseResponse;
import com.truong.common.utils.HttpClientService;
import com.truong.common.utils.Utils;
import com.truong.configuration.ApplicationPropertieConfig;
import com.truong.kafka.service.KafkaService;
import com.truong.oauth.request.LoginRequest;
import com.truong.oauth.response.Oauth2EndPointResponse;

@RestController
@RequestMapping("api")
public class AuthController extends BaseController{

	@Autowired
	private ApplicationPropertieConfig config;
	
	@Autowired
	private KafkaService kafkaService;
	
	@PostMapping("/login")
	public ResponseEntity<BaseResponse<Object>> login(@RequestBody LoginRequest loginRequest,
			@RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

		BaseResponse<Object> response = new BaseResponse<>();
		
		Oauth2EndPointResponse oauth2Response = this.login(loginRequest.getUsername(), loginRequest.getPassword(), authorizationHeader);
        
		if (oauth2Response == null || Strings.isNullOrEmpty(oauth2Response.getAccessToken())) {
			response.setStatus(HttpStatus.UNAUTHORIZED);
			response.setMessageError("Tên đăng nhập hoặc mật khẩu không chính xác");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		
		
		kafkaService.sendMessage("login", Utils.convertObjectToJsonString(oauth2Response));
		
		response.setData(oauth2Response);
        
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	private Oauth2EndPointResponse login(String username, String password, String authorizeHeader) throws Exception {
		try {
			String tokenUrl = config.getOauth2Endpoint();
			Map<String, String> headers = new HashMap<>();
			headers.put("Authorization", authorizeHeader);
			Map<String, String> params = new HashMap<>();
			params.put("grant_type", "password");
			params.put("username", username);
			params.put("password", password);
			return HttpClientService.formUrlEncoded(tokenUrl, headers, params, Oauth2EndPointResponse.class,
					0);
		} catch(Exception ex) {
			throw new CustomException(HttpStatus.UNAUTHORIZED, "Tên đăng nhập hoặc mật khẩu không chính xác");
		}
		
	}
}
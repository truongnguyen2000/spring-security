//package com.truong.oauth.security;
//
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//import org.springframework.stereotype.Component;
//
//import com.truong.oauth.entity.MediiUser;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class CustomTokenEnhancer implements TokenEnhancer {
//
//	@Override
//	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//		MediiUser mediiUser = (MediiUser) authentication.getPrincipal();
////		Map<String, Object> additionalInfo = accessToken.getAdditionalInformation();
////		additionalInfo.put("dsdsdsd", mediiUser.getId()); // Thêm thông tin ID người dùng vào token
////		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
//		return accessToken;
//	}
//}

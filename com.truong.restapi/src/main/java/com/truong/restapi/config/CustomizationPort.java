package com.truong.restapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

import com.truong.configuration.ApplicationPropertieConfig;

@Component
public class CustomizationPort implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	@Autowired
	ApplicationPropertieConfig config;


	@Override
	public void customize(ConfigurableServletWebServerFactory server) {
		System.out.println(String.format("Project start with port: %d", config.getRestapiPort()));
		server.setPort(config.getRestapiPort());
	}
}
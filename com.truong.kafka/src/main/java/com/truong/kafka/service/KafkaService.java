package com.truong.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**	
 * @author Truong
 *
 * 3 Oct 2023
 */
@Service
public class KafkaService {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String topic, String msg) {
		kafkaTemplate.send(topic, msg);
	}
}

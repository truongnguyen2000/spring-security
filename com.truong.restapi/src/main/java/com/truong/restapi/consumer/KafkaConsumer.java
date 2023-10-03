package com.truong.restapi.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.truong.common.utils.Utils;
import com.truong.entity.Post;
import com.truong.restapi.response.Oauth2EndPointResponse;
import com.truong.service.PostService;


/**	
 * @author Truong
 *
 * 3 Oct 2023
 */
@Service
public class KafkaConsumer {

	@Autowired
	PostService postService;
	
	@KafkaListener(topics = "login")
	public void listen(@Payload String message) throws Exception {
		
		Oauth2EndPointResponse data = Utils.convertJsonStringToObject(message, Oauth2EndPointResponse.class);
		
		if (data != null) {
			Post post = new Post();
			post.setTitle("Ghi log login");
			post.setContent(String.format("login token là %s", data.getAccessToken() ));
			postService.create(post);
		}
		
		
	}
}

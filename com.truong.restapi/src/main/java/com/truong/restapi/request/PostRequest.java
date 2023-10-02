package com.truong.restapi.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostRequest {

	@NotNull(message = "Tiêu đề không được null")
	@NotEmpty(message = "Tiêu đề không được rỗng")
	private String title;
	
	@NotNull(message = "Nội dung đề không được null")
	@NotEmpty(message = "Nội dung không được rỗng")
	private String content;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
} 

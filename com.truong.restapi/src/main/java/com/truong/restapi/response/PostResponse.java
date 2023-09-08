package com.truong.restapi.response;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.truong.entity.Post;

public class PostResponse {

	private int id;

	private String title;

	private String content;

	@JsonProperty("employee_id")
	private int employeeId;

	public PostResponse() {

	}

	public PostResponse(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.employeeId = post.getEmployeeId();
	}

	public List<PostResponse> mapToList(List<Post> entities){
		return entities.stream().map(x -> new PostResponse(x)).collect(Collectors.toList());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

}

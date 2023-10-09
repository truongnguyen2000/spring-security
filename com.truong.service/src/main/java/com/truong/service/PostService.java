package com.truong.service;

import java.util.List;

import com.truong.common.exception.CustomException;
import com.truong.entity.Post;

public interface PostService {
	
	public Post create(Post post);
	
	public Post spCreatePost(Post post) throws Exception;
	
	public void update(Post post);
	
	public Post findOne(int id);
	
	public List<Post> findAll(int status) throws Exception;
}

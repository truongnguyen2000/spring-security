package com.truong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truong.common.exception.CustomException;
import com.truong.dao.PostDao;
import com.truong.entity.Post;
import com.truong.service.PostService;

@Service("postService")
public class PostServiceImpl implements PostService{

	@Autowired
	PostDao dao;
	
	@Override
	public Post create(Post post) {
		return dao.create(post);
	}

	@Override
	public void update(Post post) {
		dao.update(post);
		
	}

	@Override
	public Post findOne(int id) {
		return dao.findOne(id);
	}

	@Override
	public List<Post> findAll(int status) throws Exception {
		return dao.findAll(status);
	}

}

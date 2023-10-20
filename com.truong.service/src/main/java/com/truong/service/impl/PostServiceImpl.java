package com.truong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.truong.dao.PostDao;
import com.truong.entity.Post;
import com.truong.redis.service.RedisService;
import com.truong.service.PostService;

@Service("postService")
@CacheConfig(cacheNames = "postCache")
public class PostServiceImpl implements PostService{

	@Autowired
	PostDao dao;
	
	@Autowired
	RedisService redisService;
	
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
		redisService.clearCacheByEmployeeId("postCache", (long) 1);
		return dao.findOne(id);
	}

	@Override
	@Cacheable(key = "'findAll" + ".employee_id=' + #employeeId"
			+ " + '.status=' + #status")
	public List<Post> findAll(int employeeId ,int status) throws Exception {
		return dao.findAll(employeeId, status);
	}

	@Override
	public Post spCreatePost(Post post) throws Exception {
		return dao.spCreatePost(post);
	}

}

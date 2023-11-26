package com.truong.redis.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.cache.Cache;

@Service
public class RedisService
 {

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void clearCacheByEmployeeId(String cacheName, Long employeeId) {
		String pattern = cacheName + "*employee_id=" + employeeId + "*";

        // Lấy danh sách các keys dựa trên pattern
        Set<String> cacheKeys = redisTemplate.keys(pattern);

        // Xóa các keys tìm thấy
        if (cacheKeys != null && !cacheKeys.isEmpty()) {
            redisTemplate.delete(cacheKeys);
        }
	}
}

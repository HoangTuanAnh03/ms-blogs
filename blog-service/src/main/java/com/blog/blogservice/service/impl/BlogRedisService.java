package com.blog.blogservice.service.impl;

import com.blog.blogservice.service.IBlogRedisService;
import com.blog.blogservice.service.base.impl.BaseRedisServiceImplV2;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BlogRedisService extends BaseRedisServiceImplV2<String, String, Integer> implements IBlogRedisService {

	Integer VIEW_TIME_TO_LIVE = 15*60;
	public BlogRedisService(RedisTemplate<String, Integer> redisTemplate, HashOperations<String, String, Integer> hashOperations) {
		super(redisTemplate, hashOperations);
	}

	@Override
	public boolean isUserViewed(String uid, String pid) {
		Integer i = this.hashGet("view", pid + ":" + uid);
		if(i == null) {
			boolean isFirstViewKey = !this.hashExist("view");
			this.hashSet("view", pid + ":" + uid, 1);
			if (isFirstViewKey) {
				this.setTimeToLive("view", VIEW_TIME_TO_LIVE);
			}
			return true;
		}
		return false;
	}

}

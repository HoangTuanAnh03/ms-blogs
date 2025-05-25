package com.blog.blogservice.service;

import com.blog.blogservice.service.base.BaseRedisServiceV2;
import org.springframework.stereotype.Service;

public interface IBlogRedisService extends BaseRedisServiceV2<String, String, Integer> {
	boolean isUserViewed(String uid, String pid);
}

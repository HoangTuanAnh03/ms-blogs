package com.blog.blogservice.service;

import com.blog.blogservice.dto.response.FilterResponse;

public interface IPythonService {
	public FilterResponse filterContent(String content);

	public String summaryContent(String content);
}

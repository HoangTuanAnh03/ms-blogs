package com.blog.blogservice.service;

import com.blog.blogservice.dto.response.CommentResponse;
import com.blog.blogservice.entity.Comment;

import java.util.List;

public interface ICommentService {
	public Comment addComment(Long parentId, String content, String uid, String pid);

	public List<CommentResponse> getComments(String pid);
}

package com.blog.blogservice.service.impl;

import com.blog.blogservice.advice.exception.BadRequestException;
import com.blog.blogservice.dto.mapper.CommentMapper;
import com.blog.blogservice.dto.response.CommentResponse;
import com.blog.blogservice.entity.Comment;
import com.blog.blogservice.entity.Post;
import com.blog.blogservice.repository.CommentRepository;
import com.blog.blogservice.repository.PostRepository;
import com.blog.blogservice.service.ICommentService;
import com.blog.blogservice.service.IPythonService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService implements ICommentService {
	CommentRepository commentRepository;
	IPythonService pythonService;
	PostRepository postRepository;
	CommentMapper commentMapper;
	@Override
	public Comment addComment(Long parentId, String content, String uid, String pid) {
		Post post = postRepository.findFirstByIdAndIsDeletedFalse(pid);
//				.orElseThrow(() -> new BadRequestException("Post not found"));
		if(post == null){
			throw  new BadRequestException("Post not found");
		}

		int insertLeft;
		if (parentId == null) {
			int maxRight = commentRepository.findMaxRightByPost(pid);
			insertLeft = maxRight + 1;
		}else {
			Comment parent = commentRepository.findById(parentId)
					.orElseThrow(() -> new BadRequestException("Parent comment not found"));

			int depth = commentRepository.countParents(parent.getLeftValue(), parent.getRightValue(), pid);
			if (depth >= 1) {
				throw new BadRequestException("Only original comments are allowed.");
			}

			insertLeft = parent.getRightValue();
			commentRepository.shiftLeftValues(pid, insertLeft);
			commentRepository.shiftRightValues(pid, insertLeft);
		}
		Comment comment = commentRepository.save(Comment.builder()
				.uid(uid)
				.rightValue(insertLeft + 1)
				.leftValue(insertLeft)
				.content(pythonService.filterContent(content).getFiltered_content())
				.post(post)
				.build());
		post.incrementCommentsCount();
		postRepository.save(post);
		return comment;
	}

	@Override
	public List<CommentResponse> getComments(String pid) {
		List<CommentResponse> commentResponses = commentRepository.findByPostIdOrderByLeftValueAsc(pid)
																		.stream()
																		.map(commentMapper::toCommentResponse)
																		.toList();

		return commentMapper.buildTreeFromNestedSet(commentResponses);
	}


}

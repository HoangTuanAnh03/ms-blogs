package com.blog.blogservice.dto.mapper;

import com.blog.blogservice.dto.response.PostResponse;
import com.blog.blogservice.dto.response.PostSummaryResponse;
import com.blog.blogservice.dto.response.SimpInfoUserResponse;
import com.blog.blogservice.entity.Category;
import com.blog.blogservice.entity.CategoryBlog;
import com.blog.blogservice.entity.Hashtag;
import com.blog.blogservice.entity.Post;
import com.blog.blogservice.service.IAuthService;
import com.blog.blogservice.service.impl.FollowService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostMapper {
//	AuthClient authClient;
	FollowService followService;
	CommentMapper commentMapper;
	IAuthService authService;

	public PostResponse toPostResponse(Post post, List<Post> relatedPost){
		Set<String> userIds = new HashSet<>();
		userIds.add(post.getUid());
		if (relatedPost != null) {
			userIds.addAll(relatedPost.stream()
					.map(Post::getUid)
					.collect(Collectors.toSet()));
		}

		List<SimpInfoUserResponse> users = authService.getUserByIds(new ArrayList<>(userIds));
		Map<String, SimpInfoUserResponse> userMap = users.stream()
				.collect(Collectors.toMap(SimpInfoUserResponse::getId, Function.identity()));
//		Map<String, SimpInfoUserResponse> userMap = new HashMap<>();

		return PostResponse.builder()
				.id(post.getId())
				.category(getCategoryName(post.getCategoryBlogs()))
				.commentsCount(post.getCommentsCount())
				.rawContent(post.getRawContent())
				.title(post.getTitle())
				.summaryAi(post.getSummaryAi())
				.content(post.getContent())
				.uid(post.getUid())
				.userResponse(userMap.get(post.getUid()))
				.viewsCount(post.getViewsCount())
				.cover(post.getCover())
				.hashtags(post.getHashtags().stream().map(Hashtag::getHashtag).toList())
				.hasSensitiveContent(post.isHasSensitiveContent())
				.comments(commentMapper.buildTreeFromNestedSet(
						Optional.ofNullable(post.getComments())
								.orElse(List.of())
								.stream()
								.map(commentMapper::toCommentResponse)
								.toList()
				))
				.relatedPosts(
						relatedPost == null ? List.of() :
								relatedPost.stream()
										.map(p -> toPostSummaryResponse(p, userMap.get(p.getUid())))
										.toList()
				)
				.createdAt(post.getCreatedAt())
				.build();
	}

	public PostSummaryResponse toPostSummaryResponse(Post post, SimpInfoUserResponse user){
		return PostSummaryResponse.builder()
				.id(post.getId())
				.title(post.getTitle())
				.cover(post.getCover())
				.viewsCount(post.getViewsCount())
				.category(getCategoryName(post.getCategoryBlogs()))
				.commentsCount(post.getCommentsCount())
				.hasSensitiveContent(post.isHasSensitiveContent())
				.createdAt(post.getCreatedAt())
				.userResponse(user)
				.content(post.getContent())
				.excerpt(post.getContent().length() <= 70 ?
						removeHtmlTags(post.getContent()) :
						removeHtmlTags(post.getContent().substring(0, Math.min(post.getContent().length(), 100))))
				.build();
	}

	public List<String> getCategoryName(List<CategoryBlog> categoryBlogs){
		if(categoryBlogs.isEmpty()){
			return List.of();
		}
		List<Category> categories = categoryBlogs.stream().map(CategoryBlog::getCategory).toList();
		if(categories.isEmpty()) return null;
		return categories.stream().map(Category::getCname).toList();
	}

	public String removeHtmlTags(String html) {
		return html.replaceAll("<[^>]*>", "").trim();
	}
}

package com.blog.blogservice.repository;

import com.blog.blogservice.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByLeftValueAsc(String postId);

    @Query("SELECT COALESCE(MAX(c.rightValue), 0) FROM Comment c WHERE c.post.id = :postId")
    int findMaxRightByPost(@Param("postId") String postId);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.leftValue = c.leftValue + 2 WHERE c.post.id = :postId AND c.leftValue > :from")
    void shiftLeftValues(@Param("postId") String postId, @Param("from") int from);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.rightValue = c.rightValue + 2 WHERE c.post.id = :postId AND c.rightValue >= :from")
    void shiftRightValues(@Param("postId") String postId, @Param("from") int from);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.leftValue < :left AND c.rightValue > :right AND c.post.id = :postId")
    int countParents(@Param("left") int left, @Param("right") int right, @Param("postId") String postId);
}
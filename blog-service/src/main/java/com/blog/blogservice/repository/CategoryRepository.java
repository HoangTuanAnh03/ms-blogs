package com.blog.blogservice.repository;

import com.blog.blogservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findFirstById(Long id);
	List<Category> findByIdIn(List<Long> cids);
}
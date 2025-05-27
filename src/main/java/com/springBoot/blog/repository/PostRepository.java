package com.springBoot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBoot.blog.entityy.Post;





public interface PostRepository extends JpaRepository<Post, Long>{

	List<Post> findByCategoryId(Long categoryId);
	
}

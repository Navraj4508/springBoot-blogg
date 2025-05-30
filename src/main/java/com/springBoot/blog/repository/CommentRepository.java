package com.springBoot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBoot.blog.entityy.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByPostId(long postId);
}
 
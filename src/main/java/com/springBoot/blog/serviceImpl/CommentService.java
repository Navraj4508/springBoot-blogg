package com.springBoot.blog.serviceImpl;

import java.util.List;

import com.springBoot.blog.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentById(Long postId,Long commentId);
	
	CommentDto updateComment(Long postId,Long commentId,CommentDto commentRequest);
	
	void deleteComment(Long postId,Long commentId);

}

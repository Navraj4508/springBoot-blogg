package com.springBoot.blog.serviceImpl;

import java.util.List;

import com.springBoot.blog.payload.PostDto;
import com.springBoot.blog.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);
	
	PostResponse  getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir);
	
	PostDto getPostById(Long id);
	
	PostDto updatePost(PostDto postDto, Long id);
	
	void DeletePostbyId(long id);
	
	List<PostDto> getPostsByCategory(Long categoryId);

}

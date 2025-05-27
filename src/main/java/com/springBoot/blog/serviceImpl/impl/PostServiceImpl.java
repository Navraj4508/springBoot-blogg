package com.springBoot.blog.serviceImpl.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springBoot.blog.entityy.Category;
import com.springBoot.blog.entityy.Post;
import com.springBoot.blog.exception.ResourceNotFoundException;
import com.springBoot.blog.payload.PostDto;
import com.springBoot.blog.payload.PostResponse;
import com.springBoot.blog.repository.CategoryRepository;
import com.springBoot.blog.repository.PostRepository;
import com.springBoot.blog.serviceImpl.PostService;


@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ModelMapper mapper;
	private CategoryRepository categoryRepository;
	
	
	public PostServiceImpl(PostRepository postRepository,ModelMapper mapper,
								CategoryRepository categoryRepository) {
		//super();
		this.postRepository = postRepository;
		this.mapper = mapper;
		this.categoryRepository = categoryRepository;
	}


	@Override
	public PostDto createPost(PostDto postDto) {
		
		
		//change Add Post REST API to use CategoryId
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
	    // Convert DTO to entity
//	    Post post = new Post();
//	    post.setTitle(postDto.getTitle());
//	    post.setDescription(postDto.getDescription());
//	    post.setContent(postDto.getContent());
		
		Post post = mapToEntity(postDto);
		post.setCategory(category);
	    Post newPost = postRepository.save(post);
	    
	    
	    
	    // Convert entity to DTO
	    PostDto postResponse = mapToDTo(newPost);
	 
	    return postResponse;
	}


	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
		      : Sort.by(sortBy).descending();
		
		//create pageable instance
		
		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
		Page<Post> posts = postRepository.findAll(pageable);
		
		//get content for page object
		
		List<Post> listOfPosts = posts.getContent();
		List<PostDto> content = listOfPosts.stream().map(post -> mapToDTo(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElement(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	
	}
	
	
	
	// Convert Entity to DtO
	private PostDto mapToDTo(Post post) {
		
		PostDto postDto = mapper.map(post, PostDto.class);
		
//		PostDto postResponse = new PostDto();
//	    postResponse.setId(post.getId());
//	    postResponse.setTitle(post.getTitle());
//	    postResponse.setDescription(post.getDescription());
//	    postResponse.setContent(post.getContent());
	    
	    return postDto;
	}

	
	//convert DTO to Entity
	private Post mapToEntity(PostDto postDto) {
		
		Post post = mapper.map(postDto, Post.class);
		
//		Post post = new Post();
//	    post.setTitle(postDto.getTitle());
//	    post.setDescription(postDto.getDescription());
//	    post.setContent(postDto.getContent());
	    return post;
	}


	@Override
	public PostDto getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
		return mapToDTo(post);
	}


	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		//get Post By id from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
		
		
		//change Update Post REST API to USe Category
		Category category = categoryRepository.findById(postDto.getCategoryId())
								.orElseThrow(() -> new ResourceNotFoundException("category","id",postDto.getCategoryId()));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		Post updatedPost = postRepository.save(post);
		return mapToDTo(updatedPost);
	}


	@Override
	public void DeletePostbyId(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
		postRepository.delete(post);
		
	}


	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category","id",categoryId));
		
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		
		
		return posts.stream().map((post) -> mapToDTo(post))
				.collect(Collectors.toList());
	}
	
	

	
}

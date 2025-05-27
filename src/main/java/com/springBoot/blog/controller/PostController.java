package com.springBoot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.blog.payload.PostDto;
import com.springBoot.blog.payload.PostResponse;
import com.springBoot.blog.serviceImpl.PostService;
import com.springBoot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(
		name="CRUD REST APIs for Post Resource"
)
public class PostController {

	
	private PostService postService;

	
	
	public PostController(PostService postService) {
		//super();
		this.postService = postService;
	}
	
	
	//Create blog Post
	@Operation(
			summary = "Create Post REST API",
			description = "Create Post REST API is used to save post into database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto ){
		return  new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "GET All Post REST API",
			description = "GET All Posts REST API is used to fetch all the posts from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required= false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required= false) int pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.DEFAULT_SORT_BY, required= false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.DEFAULT_SORT_DIRECTION, required= false) String sortDir
			) {
		return  postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
	}
	
	
	
	@Operation(
			summary = "GET Post By Id REST API",
			description = "GET Post By Id REST API is used to get single post from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	
	@Operation(
			summary = "Update Post REST API",
			description = "Update Post REST API is used to update a particular post in the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatedPost(@Valid @RequestBody PostDto postDto,@PathVariable(name="id") long id){
		PostDto postResponse = postService.updatePost(postDto,id);
		
		return new ResponseEntity<>(postResponse , HttpStatus.OK);
	}
	
	
	@Operation(
			summary = "Delete Post REST API",
			description = "Delete Post REST API is used to Delete a particular post from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name="id") long id){
		postService.DeletePostbyId(id);
		
		return new ResponseEntity<>("Post Entity Deleted Successfully.",HttpStatus.OK);
	}
	
	
	//Build GET Posts By CAtegory REST API 
	//(Http://localhost:8080/api/posts/categories/2)
	@GetMapping("/categories/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId){
		
		List<PostDto> postDto = postService.getPostsByCategory(categoryId);
		
		return ResponseEntity.ok(postDto);
		
	}
	
}

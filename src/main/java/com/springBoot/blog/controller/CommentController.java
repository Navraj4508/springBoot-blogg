package com.springBoot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.blog.entityy.Comment;
import com.springBoot.blog.payload.CommentDto;
import com.springBoot.blog.serviceImpl.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(
		name="CRUD REST APIs for Comment Resource"
)
public class CommentController {
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		//super();
		this.commentService = commentService;
	}
	
	@Operation(
			summary = "Create Comment REST API",
			description = "Create Comment REST API is used to save Comment into database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId")long postId,
			@Valid @RequestBody CommentDto commentDto){
		return new ResponseEntity<>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
	}
	
	
	
	@Operation(
			summary = "Get All Comments REST API",
			description = "Get All Comments REST API is used to save Comment into database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
	)
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId")long postId){
		return commentService.getCommentsByPostId(postId);
	}
	

	@Operation(
			summary = "Get Comment by id REST API",
			description = "Get Comment REST API is used to comment by id into database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
	)
	@GetMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId")Long postId,@PathVariable(value="id")Long commentId){
		CommentDto commentDto = commentService.getCommentById(postId,commentId);
		
		return new ResponseEntity<>(commentDto , HttpStatus.OK);
	}
	
	@Operation(
			summary = "Update Comment REST API",
			description = "Update comment REST API is used to update a particular data into database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updatedComment(@PathVariable(value= "postId")Long postId,
			                                         @PathVariable(value= "id")Long commentId,
			                                         @Valid @RequestBody CommentDto commentDto){
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		return new ResponseEntity<>(updatedComment,HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete Comment REST API",
			description = "Delete Comment REST API is used to Id from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable(value="postId")Long postId,
												@PathVariable(value="id")Long commentId){
		commentService.deleteComment(postId,commentId);
		return new ResponseEntity<>("Comment Deleted Successfully",HttpStatus.OK);
	}
	
	
	
}

package com.springBoot.blog.serviceImpl.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springBoot.blog.entityy.Comment;
import com.springBoot.blog.entityy.Post;
import com.springBoot.blog.exception.BlogAPIException;
import com.springBoot.blog.exception.ResourceNotFoundException;
import com.springBoot.blog.payload.CommentDto;
import com.springBoot.blog.repository.CommentRepository;
import com.springBoot.blog.repository.PostRepository;
import com.springBoot.blog.serviceImpl.CommentService;


@Service
public class CommentserviceImpl  implements CommentService {
	
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;
	

	public CommentserviceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
		//super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper= mapper;
	}


	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
		
		// Retrive post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
		
		
		//set Post to comment Entity 
		comment.setPost(post);
		
		//comment entity to DB
		Comment newComment  = commentRepository.save(comment);
		
		
		return mapToDto(newComment);
	}

	
	private CommentDto mapToDto(Comment comment) {
		
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
		
		return commentDto;
	}
	
	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
	//	Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
		
		return comment;
	}





	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		
		//Retrieve comment by postId
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		//Convert list of comment entities to list of comment dto's
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}


	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		
		
		// Retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
		
		//retrieve comment by  id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
		}
		
		return mapToDto(comment);
	}


	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
		
		
		// Retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
			
		
		//retrieve comment by  id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
				
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_GATEWAY,"Comment does not belongs to post");
		}
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		return mapToDto(updatedComment);
		
	}


	@Override
	public void deleteComment(Long postId, Long commentId) {
		
		// Retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
		
		//retrieve comment by  id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_GATEWAY,"Comment does not belongs to post");
		}
	
		commentRepository.delete(comment);
		
		
	}
	
	
	
	
	
}

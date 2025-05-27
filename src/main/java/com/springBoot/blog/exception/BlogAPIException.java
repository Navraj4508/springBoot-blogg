package com.springBoot.blog.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//(We throw this exception whenever we write some business logic or validate request parameters).


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogAPIException extends RuntimeException {

	private HttpStatus status;
	private String messsage;
	
	
	
	
}

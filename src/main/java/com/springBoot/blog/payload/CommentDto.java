package com.springBoot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

	private long id;
	
	@Schema(
			description = "CommentDto Name"
	)
	@NotEmpty(message="Name should not be null and empty")
	
	private String name;
	@Schema(
			description = "CommentDto Email"
	)
	@NotEmpty(message="Email should not be null and empty")
	@Email
	private String email;
	@Schema(
			description = "CommentDto Body"
	)
	@NotEmpty(message="Body  should not be null and empty")
	@Size(min= 10,message = "Comment body  must  be minimum 10 Character")
	private String body;
}

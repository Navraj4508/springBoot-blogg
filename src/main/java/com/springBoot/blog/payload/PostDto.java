package com.springBoot.blog.payload;


import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
		description = "PostDto Model Information"
)
public class PostDto {

	private Long id;
	
	@Schema(
			description = "PostDto Title "
	)
	//title should  not be null or empty
	//title should have at  least 2 characters
	@NotEmpty
	@Size(min = 2,message="Post title should have at  least 2 characters")
	private String title;
	
	@Schema(
			description = "PostDto Description"
	)
	@NotEmpty
	@Size(min = 10,message="Post Description should have at least 10 characters")
	private String description;
	
	
	@Schema(
			description = "PostDto Content"
	)
	@NotEmpty
	private String content;
	
	private Set<CommentDto> comments;
	
	@Schema(
			description = "PostDto CategoryId"
	)
	private Long categoryId;
	
}

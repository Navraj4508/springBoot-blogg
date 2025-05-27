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
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.blog.payload.CategoryDto;
import com.springBoot.blog.serviceImpl.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(
		name="CRUD REST APIs for Category Resource"
)
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}
	
	
	//Build Add Category REST API
	@Operation(
			summary = "Create Category REST API",
			description = "Create Category REST API is used to save Cateegory into database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		 CategoryDto savedCategory = categoryService.addCategory(categoryDto);
		 
		 return new ResponseEntity<>(savedCategory,HttpStatus.CREATED);
	}
	
	
	//Build Get Category REST API
	@Operation(
			summary = "Get Category REST API",
			description = "Get Category REST API is used to category By id  into database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
	)
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id")Long CategoryId){
		CategoryDto  categoryDto = categoryService.getCategory(CategoryId);
		
		return ResponseEntity.ok(categoryDto);
	}
	
	
	//build GET Category Rest API 
	@Operation(
			summary = "GET All Category REST API",
			description = "Get all Category REST API is used to fetch Category  from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
	)
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getCategories(){
		
		return ResponseEntity.ok(categoryService.gettAllCategory());
	}
	
	
	//Build Update Category REST API
	@Operation(
			summary = "Update Category REST API",
			description = "Update Category REST API is used to update by Id into database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
															@PathVariable("id") Long categoryId){
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
	}
	
	
	//Delete Category REST API
	@Operation(
			summary = "Delete Category REST API",
			description = "Delete Category REST API is used to delete a particular data into database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category Deleted Successfully!.");
	}
	
	
}

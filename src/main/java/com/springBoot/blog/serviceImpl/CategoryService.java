package com.springBoot.blog.serviceImpl;

import java.util.List;

import com.springBoot.blog.payload.CategoryDto;

public interface CategoryService {

	CategoryDto addCategory(CategoryDto categoryDto);
	
	CategoryDto getCategory(Long categoryId);
	
	List<CategoryDto> gettAllCategory();
	
	CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
	
	void deleteCategory(Long categoryId);
}

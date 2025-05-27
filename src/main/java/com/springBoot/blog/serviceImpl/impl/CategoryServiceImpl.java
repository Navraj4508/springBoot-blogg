package com.springBoot.blog.serviceImpl.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springBoot.blog.entityy.Category;
import com.springBoot.blog.exception.ResourceNotFoundException;
import com.springBoot.blog.payload.CategoryDto;
import com.springBoot.blog.repository.CategoryRepository;
import com.springBoot.blog.serviceImpl.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;
	
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}
 

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		return modelMapper.map(savedCategory, CategoryDto.class);
	}


	@Override
	public CategoryDto getCategory(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
		.orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));
		
		
		
		return modelMapper.map(category, CategoryDto.class);
	}


	@Override
	public List<CategoryDto> gettAllCategory() {
		List<Category> categories = categoryRepository.findAll();
		
		return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
				.collect(Collectors.toList());
	}


	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setId(categoryId);
		
		Category updatedCategory = categoryRepository.save(category);
		
		
		return modelMapper.map(updatedCategory, CategoryDto.class);
	}


	@Override
	public void deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		categoryRepository.delete(category);
		
		
	}

	
	
	
	
}

package com.springBoot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBoot.blog.entityy.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}

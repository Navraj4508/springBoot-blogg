package com.springBoot.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBoot.blog.entityy.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByemail(String email);
	
	Optional<User> findByUsernameOrEmail(String username,String email);
	
	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
}

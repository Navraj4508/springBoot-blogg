package com.springBoot.blog.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springBoot.blog.entityy.Role;





public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
	
	
	
}

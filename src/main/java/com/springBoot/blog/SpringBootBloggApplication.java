package com.springBoot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.springBoot.blog.entityy.Role;
import com.springBoot.blog.repository.RoleRepository;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App REST APIs",
				description = "Spring Boot Blog App REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Navraj Kumar",
						email = "navrajk4508@gmail.com"
						
				),
				license = @License(
						name = "Apache 10.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/Navraj4508"
		)
)
public class SpringBootBloggApplication implements CommandLineRunner {
	
	
	
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBloggApplication.class, args);
	}

	@Autowired
	private RoleRepository rolerepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		rolerepository.save(adminRole);
		
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		rolerepository.save(userRole);
		
		
	}
	
	
	

}

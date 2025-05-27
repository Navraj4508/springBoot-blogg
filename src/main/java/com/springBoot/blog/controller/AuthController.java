package com.springBoot.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.blog.payload.JWTAuthResponse;
import com.springBoot.blog.payload.LoginDto;
import com.springBoot.blog.payload.RegisterDto;
import com.springBoot.blog.serviceImpl.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	
	//Build Login Rest API
	
	@PostMapping(value = {"/login","/sigin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
		String token = authService.login(loginDto);
		
		JWTAuthResponse jwtAuthResponse  = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		
		return ResponseEntity.ok(jwtAuthResponse);
	}
	
	//Build Register REST API
	@PostMapping(value = {"/register","/sigup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	 
}

package com.springBoot.blog.serviceImpl;

import com.springBoot.blog.payload.LoginDto;
import com.springBoot.blog.payload.RegisterDto;

public interface AuthService {

	String login(LoginDto loginDto);
	
	String register(RegisterDto registerDto);
}

package com.springBoot.blog.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springBoot.blog.exception.BlogAPIException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;
	
	//generate JWT Token
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();   //- Retrieves the username from the Authentication object.

		
		Date currentDate = new Date();
		
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		
		/*- Uses Jwts.builder() to:
		- Set the subject (username).
		- Define issuance and expiration timestamps.
		- Sign the token using a method key().
		- Convert the token to a compact string.
         */
		//###################################
		
		/*
		 * Jwts.builder() is a method from the JJWT library, 
		 * which is used in Java to create JSON Web Tokens (JWTs).
		 *  It allows developers to build and customize JWTs by
		 *   setting claims, expiration times, subjects,
		 *    and other attributes before signing the token.
		 * 
		*/
		
		String token = Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(expireDate)
				.signWith(key())
				.compact();
		
		return token;
	}
	
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	
	// get username from JWT tokwn
	
	public String getUsername(String token) {
		
		return Jwts.parser()
				.verifyWith((SecretKey) key())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	
	//Validate JWT token
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parser()
				.verifyWith((SecretKey) key())
				.build()
				.parse(token);
			
			return true;
			
		}catch(MalformedJwtException malformedJwtException) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
		}catch(ExpiredJwtException expiredJwtException) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Expired JWT Token");
		}catch(UnsupportedJwtException unsupportedJwtException) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"UnSupported JWT Token");
		}catch(IllegalArgumentException illegalArgumentException) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Jwt claims String is null or empty");
		}
	}
	
	
}

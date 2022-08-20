package com.realestate.authorization.controller;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;


import com.realestate.authorization.exceptions.AuthorizationException;
import com.realestate.authorization.exceptions.UserNotFoundException;
import com.realestate.authorization.services.JwtUserDetailsService;
import com.realestate.authorization.entities.JwtRequest;
import com.realestate.authorization.entities.JwtResponse;
import com.realestate.authorization.entities.User;
import com.realestate.authorization.controllers.JwtAuthenticationController;
import com.realestate.authorization.configs.JwtTokenUtil;


@SpringBootTest
@Configuration
public class AuthControllerTest {


	@Autowired
private JwtAuthenticationController authController;
   @Autowired
   JwtUserDetailsService userDetailsService;

   @Test
	void contextLoads() {

		assertNotNull(authController);

	}
	
	@Test
	void createAuthenticationTokenTest() throws Exception {
		
		this.generateToken();	
	}
	
	@Test
	void registerTest() throws Exception{
		ResponseEntity<String> s=this.authController.saveUser(new User("xyz","1234"));
		
		assertEquals("User Created Successfully!",s.getBody());
		
		
	}
	// Generating token
	private JwtResponse generateToken() throws Exception{	
		
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("shivani");
		jwtRequest.setPassword("1234");
		JwtResponse res = null;
		try {
			ResponseEntity<JwtResponse> createAuthenticationToken = this.authController
					.createAuthenticationToken(jwtRequest);
			 res =  createAuthenticationToken.getBody();
			 
		}
		catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return res;
	}
}


package com.realestate.authorization.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.authorization.configs.JwtTokenUtil;
import com.realestate.authorization.entities.JwtRequest;
import com.realestate.authorization.entities.JwtResponse;
import com.realestate.authorization.entities.User;
import com.realestate.authorization.exceptions.AuthorizationException;
import com.realestate.authorization.exceptions.UserNotFoundException;
import com.realestate.authorization.services.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws UserNotFoundException {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	private Authentication  authenticate(String username, String password) throws UserNotFoundException {
		try {
			Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return auth;
		} catch (DisabledException e) {
			throw new UserNotFoundException("USER_DISABLED");
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new UserNotFoundException("INVALID_CREDENTIALS");
		}
	}

	@PostMapping(value = "/register")
	public ResponseEntity<String> saveUser(@RequestBody User user) throws Exception {
		boolean res=userDetailsService.save(user);
		return new ResponseEntity<String>("User Created Successfully!",HttpStatus.CREATED);
		
	}
	
	@PostMapping(value = "/authorize")
	public boolean authorizeTheRequest(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
		String jwtToken = null;
		String userName = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException | ExpiredJwtException e) {
				return false;
			}
		}
		return userName!=null;
	}
	
}

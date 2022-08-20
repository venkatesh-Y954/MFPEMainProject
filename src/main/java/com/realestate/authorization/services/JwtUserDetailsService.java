package com.realestate.authorization.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.authorization.entities.User;
import com.realestate.authorization.exceptions.UserAlredyExistsException;
import com.realestate.authorization.repos.UserRepository;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(userName);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + userName);
		}
		log.info("User found");
		log.info("user successfully located");
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}
	
	@Transactional
	public boolean save(User user) throws Exception{
		boolean flag=false;
		
		if(CheckIfUserAlreadyExists(user.getUsername())) {
			flag=false;
		
		   
			throw new UserAlredyExistsException("User with user name "+user.getUsername()+" already exists");
			
			        
		}
		
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		
		userRepo.save(newUser);
		flag=true;
		log.info("user successfully saved!");
		
		return flag;
		
	}
	
	public boolean CheckIfUserAlreadyExists(String username) {
		return userRepo.findAll().stream().anyMatch(u -> u.getUsername().equals(username));
	}
}

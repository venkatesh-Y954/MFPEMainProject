package com.realestate.authorization.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.authorization.entities.User;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String userName);
	
	/*
	  @Query("SELECT CASE WHEN COUNT(s)>0 THEN TRUE ELSE FALSE END FROM User s WHERE s.personId=:id")
	Boolean isUserExistsById(Integer id); 
*/
}

package com.realestate.customer.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestate.customer.entities.Requirement;

public interface RequirementRepository extends JpaRepository<Requirement, Integer> {

}

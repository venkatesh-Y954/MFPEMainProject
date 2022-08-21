package com.realestate.customer.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestate.customer.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}

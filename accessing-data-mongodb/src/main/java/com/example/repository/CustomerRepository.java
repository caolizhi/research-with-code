package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	public Customer findByFirstName(String firstName);

}

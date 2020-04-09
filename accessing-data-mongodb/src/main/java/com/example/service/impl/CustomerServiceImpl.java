package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public List<Customer> getCustomers() {
		return mongoTemplate.findAll(Customer.class);
	}

	@Override
	public Customer getCustomersByFirstName(String firstName) {
		return customerRepository.findByFirstName(firstName);
	}

	@Override
	public List<Customer> getCustomersByLastName(String lastName) {
		Criteria criteria = Criteria.where("lastName").is(lastName);
		Query query = new Query(criteria);
		return mongoTemplate.find(query,Customer.class);
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return mongoTemplate.save(customer);
	}

}

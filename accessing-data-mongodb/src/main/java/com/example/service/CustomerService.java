package com.example.service;

import java.util.List;

import com.example.model.Customer;

public interface CustomerService {

	List<Customer> getCustomers();

	Customer getCustomersByFirstName(String firstName);

	List<Customer> getCustomersByLastName(String lastName);

	Customer saveCustomer(Customer customer);
}


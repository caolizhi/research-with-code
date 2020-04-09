package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Customer;
import com.example.service.CustomerService;

@RestController
@RequestMapping()
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return customerService.getCustomers();
	}

	@GetMapping("/customers/first-name/{first-name}")
	public Customer getCustomersByFirstName(@PathVariable("first-name") String firstName) {
		return customerService.getCustomersByFirstName(firstName);
	}

	@GetMapping("/customers/{last-name}")
	public List<Customer> getCustomersByLastName(@PathVariable("last-name") String lastName) {
		return customerService.getCustomersByLastName(lastName);
	}

	@PostMapping("/customers")
	public Customer saveCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}
}

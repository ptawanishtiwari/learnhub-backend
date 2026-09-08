package com.example.parth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parth.entity.Customer;
import com.example.parth.service.CustomerService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
      
	@PostMapping("/create")
	public Customer create(@RequestBody Customer customer) {
		
		return customerService.create(customer);
		
	}
}

package com.example.parth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.parth.entity.Customer;
import com.example.parth.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer create(Customer customer) {
		return customerRepository.save(customer);
	}

	public List<Customer> getAll() {
		
		return customerRepository.findAll();
	}

}

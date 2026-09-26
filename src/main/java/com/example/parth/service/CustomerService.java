package com.example.parth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.parth.entity.Customer;
import com.example.parth.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public Customer create(Customer customer) {
		customer.setRole("USER");
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
		return customerRepository.save(customer);
	}

	public List<Customer> getAll() {
		
		return customerRepository.findAll();
	}
	


	public Customer getCustomerById(int id) {
		
		return customerRepository.findById(id).orElse(null);
	}

	public Customer updateById(int id, Customer user) {
		Customer existCustomer  = customerRepository.findById(id).orElse(null);
		if(existCustomer != null) {
			existCustomer.setEmail(user.getEmail());
			existCustomer.setName(user.getName());
			existCustomer.setPhone(user.getPhone());
			
			return customerRepository.save(existCustomer);
		}
		
		return null;
	}

	public void deleteById(int id) {
		customerRepository.deleteById(id);
	}
	
	
//	login code 
	
	public Customer login(String email, String password) {

	    authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    email,
	                    password
	            )
	    );

	    return customerRepository
	            .findByEmail(email)
	            .orElseThrow(() ->
	                    new RuntimeException(
	                            "Customer not found with email: " + email
	                    )
	            );
	}

}

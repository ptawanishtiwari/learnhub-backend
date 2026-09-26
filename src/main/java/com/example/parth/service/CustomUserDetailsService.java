package com.example.parth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.parth.entity.Customer;
import com.example.parth.repository.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Customer customer = customerRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Customer Not Found"));
		
		
		return User.builder()
				.username(customer.getEmail())
				.password(customer.getPassword())
				.roles(customer.getRole())
				.build();
				
	}
	

}

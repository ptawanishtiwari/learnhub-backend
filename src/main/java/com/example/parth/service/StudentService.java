package com.example.parth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parth.entity.User;
import com.example.parth.repository.UserRepository;

@Service
public class StudentService {
	
	@Autowired
	private UserRepository userRepository;

	public User saveStudent(User user) {
		
		System.out.println("student data sent to repository layer");
		
		
		 return  userRepository.save(user);
		 
		
		
		
	}

	public List<User> getAllStudents() {
		
		return userRepository.findAll();
	}

	public User getStudentById(Long id) {
		
		return userRepository.findById(id).orElse(null);
	}
	
	

	public void deleteById(long id) {
		userRepository.deleteById(id);
		
	}

	public void deleteAllStudent() {
		userRepository.deleteAll();
		
	}

	public User updateById(long id, User user) {
		User existUser = userRepository.findById(id).orElse(null);
		if(existUser != null) {
		       existUser.setName(user.getName());
			existUser.setEmail(user.getEmail());
			existUser.setPassword(user.getPassword());
			
			return userRepository.save(existUser);
			
		}
		return null;
	}

	public String updatePassword(User user) {

	    List<User> users = userRepository.findAll();

	    for (User existUser : users) {

	        existUser.setPassword(user.getPassword());

	        userRepository.save(existUser);
	    }

	    return "Password updated for all users";
	}
	
	

}

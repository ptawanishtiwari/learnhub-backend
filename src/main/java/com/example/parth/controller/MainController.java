package com.example.parth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parth.entity.User;
import com.example.parth.repository.UserRepository;
import com.example.parth.service.StudentService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/students")
public class MainController {
	
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/")
	public String Home() {
		return "Welcome to the Home Page " ;
	}
	
//	Register any student
	
	@PostMapping("/create")
	public User Register(@RequestBody User user) {
	       System.out.println("Name : "+user.getName());
	       System.out.println("Email : "+user.getEmail());
	       
	       System.out.println("Student data sent to service");
	       return  studentService.saveStudent(user);
	      
		
	}
//	Get all student Data
	
	@GetMapping("/all")
	public List<User> getAllStudent(){
		return studentService.getAllStudents();
	}
	
// Get Single student data
	
	@GetMapping("{id}")
	public User getSingleUser(@PathVariable Long id) {
		return studentService.getStudentById(id);
	}
	
	
// Updata Student By id 
	
	@PutMapping("/{id}")
	public User updateStudent(@PathVariable long id , 
			@RequestBody User  user) {
		return studentService.updateById(id,user);
		
	} 
	
	
// Update all users password
	@PutMapping("/all")
	public String updatePassword(@RequestBody User user) {
		return studentService.updatePassword(user);
	}
	
	
// Delete By Id
	@DeleteMapping("{id}")
	public String  deleteById(@PathVariable long id) {
		studentService.deleteById(id);
		return   "Student deleted Successfully ";
	}
	
	
// Delete all student data
	@DeleteMapping("delete-all")
	public String deleteAllStudent() {
		studentService.deleteAllStudent();
		return "All student data has been  deleted";
	}
}

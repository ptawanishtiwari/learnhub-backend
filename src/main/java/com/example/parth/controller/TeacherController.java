package com.example.parth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parth.entity.Teacher;
import com.example.parth.service.TeacherService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/teacher")
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	@PostMapping("/create")
	public Teacher Teach(@RequestBody  Teacher teacher) {
		return teacherService.saveteacher(teacher);
	}
	
	@GetMapping("/all-teachers")
	public List<Teacher> teachers(){
		return teacherService.getAllTeachers();
	}
	
	@GetMapping("/{id}")
	public Teacher singleTeacher(@PathVariable Long id) {
		return teacherService.getTeacherById(id);
	}
	
	@DeleteMapping("/{id}")
	public String DeleteUser(@PathVariable Long id) {
		teacherService.deleteByid(id);
		return   "Teacher deleted Successfully ";
				
	}
	
	@DeleteMapping("/delete-all")
	public String deleteallteacher() {
		teacherService.deleteallteachers();
		return "all teacher deleted";
	}

}

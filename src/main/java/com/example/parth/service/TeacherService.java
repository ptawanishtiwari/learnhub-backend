package com.example.parth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parth.entity.Teacher;
import com.example.parth.repository.TeacherRepository;
import com.example.parth.repository.UserRepository;

@Service
public class TeacherService {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
//  Get all teachers in list 
	public List<Teacher> getAllTeachers(){
		return teacherRepository.findAll();
	}
	
//	registrer any teacher 

	public Teacher saveteacher(Teacher teacher) {
		
		return teacherRepository.save(teacher);
	}
	
//	find teacher by id 

	public Teacher getTeacherById(Long id) {
		
		return teacherRepository.findById(id).orElse(null);
	}
	
//	delete By Id 

	public void deleteByid(Long id) {
		teacherRepository.deleteById(id);
	}

	public void deleteallteachers() {
		teacherRepository.deleteAll();
	}

}

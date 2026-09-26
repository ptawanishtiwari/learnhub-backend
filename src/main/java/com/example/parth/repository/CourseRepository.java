package com.example.parth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parth.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
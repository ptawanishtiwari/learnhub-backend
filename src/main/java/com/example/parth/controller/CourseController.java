package com.example.parth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parth.entity.Course;
import com.example.parth.service.CourseService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // CREATE COURSE
    @PostMapping("/create")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    // GET ALL COURSES
    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // GET SINGLE COURSE
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }

    // UPDATE COURSE
    @PutMapping("/{id}")
    public Course updateCourse(
            @PathVariable int id,
            @RequestBody Course course) {

        return courseService.updateCourse(id, course);
    }

    // DELETE COURSE
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {

        courseService.deleteCourse(id);

        return "Course deleted successfully";
    }
}
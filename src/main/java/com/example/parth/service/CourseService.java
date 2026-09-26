package com.example.parth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parth.entity.Course;
import com.example.parth.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Create Course
    public Course createCourse(Course course) {

        if (course.getStatus() == null || course.getStatus().isEmpty()) {
            course.setStatus("DRAFT");
        }

        return courseRepository.save(course);
    }

    // Get All Courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get Course By ID
    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    // Update Course
    public Course updateCourse(int id, Course course) {

        Course existingCourse =
                courseRepository.findById(id).orElse(null);

        if (existingCourse == null) {
            return null;
        }

        existingCourse.setTitle(course.getTitle());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setCategory(course.getCategory());
        existingCourse.setLevel(course.getLevel());
        existingCourse.setPrice(course.getPrice());
        existingCourse.setThumbnail(course.getThumbnail());
        existingCourse.setDuration(course.getDuration());
        existingCourse.setStatus(course.getStatus());

        return courseRepository.save(existingCourse);
    }

    // Delete Course
    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }
}
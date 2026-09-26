package com.example.parth.service;

import com.example.parth.entity.Enrollment;
import com.example.parth.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Create Enrollment
    public Enrollment createEnrollment(Enrollment enrollment) {

        // Check duplicate enrollment
        Optional<Enrollment> existingEnrollment =
                enrollmentRepository.findByCustomerIdAndCourseId(
                        enrollment.getCustomerId(),
                        enrollment.getCourseId()
                );

        if (existingEnrollment.isPresent()) {
            throw new RuntimeException(
                    "Student is already enrolled in this course"
            );
        }

        // Automatically set enrollment date
        enrollment.setEnrollmentDate(LocalDateTime.now());

        // Default status
        if (enrollment.getStatus() == null ||
                enrollment.getStatus().trim().isEmpty()) {

            enrollment.setStatus("ACTIVE");
        }

        return enrollmentRepository.save(enrollment);
    }

    // Get All Enrollments
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // Get Enrollment By ID
    public Enrollment getEnrollmentById(int id) {

        return enrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Enrollment not found with id: " + id
                        )
                );
    }

    // Get Enrollments By Customer
    public List<Enrollment> getEnrollmentsByCustomer(int customerId) {

        return enrollmentRepository.findByCustomerId(customerId);
    }

    // Get Enrollments By Course
    public List<Enrollment> getEnrollmentsByCourse(int courseId) {

        return enrollmentRepository.findByCourseId(courseId);
    }

    // Update Enrollment
    public Enrollment updateEnrollment(
            int id,
            Enrollment updatedEnrollment) {

        Enrollment existingEnrollment = getEnrollmentById(id);

        existingEnrollment.setCustomerId(
                updatedEnrollment.getCustomerId()
        );

        existingEnrollment.setCourseId(
                updatedEnrollment.getCourseId()
        );

        if (updatedEnrollment.getStatus() != null &&
                !updatedEnrollment.getStatus().trim().isEmpty()) {

            existingEnrollment.setStatus(
                    updatedEnrollment.getStatus()
            );
        }

        return enrollmentRepository.save(existingEnrollment);
    }

    // Update Only Status
    public Enrollment updateStatus(int id, String status) {

        Enrollment enrollment = getEnrollmentById(id);

        enrollment.setStatus(status);

        return enrollmentRepository.save(enrollment);
    }

    // Delete Enrollment
    public void deleteEnrollment(int id) {

        Enrollment enrollment = getEnrollmentById(id);

        enrollmentRepository.delete(enrollment);
    }
}
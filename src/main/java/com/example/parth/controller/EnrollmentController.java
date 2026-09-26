package com.example.parth.controller;

import com.example.parth.entity.Enrollment;
import com.example.parth.service.EnrollmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // ==========================================
    // CREATE ENROLLMENT
    // POST /enrollment/create
    // ==========================================

    @PostMapping("/create")
    public Enrollment createEnrollment(
            @RequestBody Enrollment enrollment) {

        return enrollmentService.createEnrollment(enrollment);
    }

    // ==========================================
    // GET ALL ENROLLMENTS
    // GET /enrollment/all
    // ==========================================

    @GetMapping("/all")
    public List<Enrollment> getAllEnrollments() {

        return enrollmentService.getAllEnrollments();
    }

    // ==========================================
    // GET ENROLLMENT BY ID
    // GET /enrollment/{id}
    // ==========================================

    @GetMapping("/{id}")
    public Enrollment getEnrollmentById(
            @PathVariable int id) {

        return enrollmentService.getEnrollmentById(id);
    }

    // ==========================================
    // GET BY CUSTOMER
    // GET /enrollment/customer/{customerId}
    // ==========================================

    @GetMapping("/customer/{customerId}")
    public List<Enrollment> getByCustomer(
            @PathVariable int customerId) {

        return enrollmentService.getEnrollmentsByCustomer(customerId);
    }

    // ==========================================
    // GET BY COURSE
    // GET /enrollment/course/{courseId}
    // ==========================================

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getByCourse(
            @PathVariable int courseId) {

        return enrollmentService.getEnrollmentsByCourse(courseId);
    }

    // ==========================================
    // UPDATE ENROLLMENT
    // PUT /enrollment/{id}
    // ==========================================

    @PutMapping("/{id}")
    public Enrollment updateEnrollment(
            @PathVariable int id,
            @RequestBody Enrollment enrollment) {

        return enrollmentService.updateEnrollment(
                id,
                enrollment
        );
    }

    // ==========================================
    // UPDATE STATUS
    // PUT /enrollment/{id}/status
    // ==========================================

    @PutMapping("/{id}/status")
    public Enrollment updateStatus(
            @PathVariable int id,
            @RequestBody Map<String, String> request) {

        String status = request.get("status");

        return enrollmentService.updateStatus(
                id,
                status
        );
    }

    // ==========================================
    // DELETE ENROLLMENT
    // DELETE /enrollment/{id}
    // ==========================================

    @DeleteMapping("/{id}")
    public String deleteEnrollment(
            @PathVariable int id) {

        enrollmentService.deleteEnrollment(id);

        return "Enrollment deleted successfully";
    }
}
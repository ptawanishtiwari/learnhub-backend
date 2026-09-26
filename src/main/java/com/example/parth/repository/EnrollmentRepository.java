package com.example.parth.repository;

import com.example.parth.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    List<Enrollment> findByCustomerId(int customerId);

    List<Enrollment> findByCourseId(int courseId);

    Optional<Enrollment> findByCustomerIdAndCourseId(
            int customerId,
            int courseId
    );
}
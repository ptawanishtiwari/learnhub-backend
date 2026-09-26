package com.example.parth.repository;

import com.example.parth.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    // =========================================================
    // FIND PAYMENTS
    // =========================================================

    List<Payment> findByCustomerId(int customerId);

    List<Payment> findByCourseId(int courseId);

    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

    Optional<Payment> findByRazorpayPaymentId(String razorpayPaymentId);


    // =========================================================
    // FIND BY PAYMENT STATUS
    // =========================================================

    List<Payment> findByStatus(String status);


    // =========================================================
    // COUNT PAYMENTS BY STATUS
    // =========================================================

    long countByStatus(String status);


    // =========================================================
    // TOTAL SUCCESSFUL REVENUE
    // =========================================================

    @Query("""
            SELECT COALESCE(SUM(p.amount), 0)
            FROM Payment p
            WHERE p.status = :status
            """)
    Double getTotalAmountByStatus(@Param("status") String status);
}
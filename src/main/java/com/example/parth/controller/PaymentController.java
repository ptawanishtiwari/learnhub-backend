package com.example.parth.controller;

import com.example.parth.entity.Payment;
import com.example.parth.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    // =========================================================
    // 1. CREATE RAZORPAY ORDER
    // =========================================================

    @PostMapping("/create-order")
    public String createOrder(
            @RequestParam int customerId,
            @RequestParam int courseId) {

        JSONObject response =
                paymentService.createOrder(
                        customerId,
                        courseId
                );

        return response.toString();
    }


    // =========================================================
    // 2. VERIFY RAZORPAY PAYMENT
    // =========================================================

    @PostMapping("/verify")
    public String verifyPayment(
            @RequestParam int customerId,
            @RequestParam int courseId,
            @RequestParam String razorpayOrderId,
            @RequestParam String razorpayPaymentId,
            @RequestParam String razorpaySignature) {

        JSONObject response =
                paymentService.verifyPayment(
                        customerId,
                        courseId,
                        razorpayOrderId,
                        razorpayPaymentId,
                        razorpaySignature
                );

        return response.toString();
    }


    // =========================================================
    // 3. GET ALL PAYMENTS
    // =========================================================

    @GetMapping("/all")
    public List<Payment> getAllPayments() {

        return paymentService.getAllPayments();
    }


    // =========================================================
    // 4. GET PAYMENT BY ID
    // =========================================================

    @GetMapping("/{id}")
    public Payment getPaymentById(
            @PathVariable int id) {

        return paymentService.getPaymentById(id);
    }


    // =========================================================
    // 5. GET PAYMENTS BY CUSTOMER
    // =========================================================

    @GetMapping("/customer/{customerId}")
    public List<Payment> getPaymentsByCustomer(
            @PathVariable int customerId) {

        return paymentService
                .getPaymentsByCustomer(customerId);
    }


    // =========================================================
    // 6. GET PAYMENTS BY COURSE
    // =========================================================

    @GetMapping("/course/{courseId}")
    public List<Payment> getPaymentsByCourse(
            @PathVariable int courseId) {

        return paymentService
                .getPaymentsByCourse(courseId);
    }


    // =========================================================
    // 7. GET PAYMENT BY RAZORPAY ORDER ID
    // =========================================================

    @GetMapping("/order/{orderId}")
    public Payment getPaymentByOrderId(
            @PathVariable String orderId) {

        return paymentService
                .getPaymentByOrderId(orderId);
    }


    // =========================================================
    // 8. GET PAYMENT BY RAZORPAY PAYMENT ID
    // =========================================================

    @GetMapping("/razorpay-payment/{paymentId}")
    public Payment getPaymentByRazorpayPaymentId(
            @PathVariable String paymentId) {

        return paymentService
                .getPaymentByRazorpayPaymentId(paymentId);
    }


    // =========================================================
    // 9. GET PAYMENTS BY STATUS
    // =========================================================

    @GetMapping("/status/{status}")
    public List<Payment> getPaymentsByStatus(
            @PathVariable String status) {

        return paymentService
                .getPaymentsByStatus(status);
    }


    // =========================================================
    // 10. GET SUCCESSFUL PAYMENTS
    // =========================================================

    @GetMapping("/success")
    public List<Payment> getSuccessfulPayments() {

        return paymentService
                .getSuccessfulPayments();
    }


    // =========================================================
    // 11. GET PENDING PAYMENTS
    // =========================================================

    @GetMapping("/pending")
    public List<Payment> getPendingPayments() {

        return paymentService
                .getPendingPayments();
    }


    // =========================================================
    // 12. GET FAILED PAYMENTS
    // =========================================================

    @GetMapping("/failed")
    public List<Payment> getFailedPayments() {

        return paymentService
                .getFailedPayments();
    }


    // =========================================================
    // 13. GET TOTAL PAYMENT COUNT
    // =========================================================

    @GetMapping("/count")
    public long getPaymentCount() {

        return paymentService
                .getPaymentCount();
    }


    // =========================================================
    // 14. GET SUCCESSFUL PAYMENT COUNT
    // =========================================================

    @GetMapping("/count/success")
    public long getSuccessfulPaymentCount() {

        return paymentService
                .getSuccessfulPaymentCount();
    }


    // =========================================================
    // 15. GET PENDING PAYMENT COUNT
    // =========================================================

    @GetMapping("/count/pending")
    public long getPendingPaymentCount() {

        return paymentService
                .getPendingPaymentCount();
    }


    // =========================================================
    // 16. GET FAILED PAYMENT COUNT
    // =========================================================

    @GetMapping("/count/failed")
    public long getFailedPaymentCount() {

        return paymentService
                .getFailedPaymentCount();
    }


    // =========================================================
    // 17. GET TOTAL SUCCESSFUL REVENUE
    // =========================================================

    @GetMapping("/revenue")
    public Double getTotalSuccessfulRevenue() {

        return paymentService
                .getTotalSuccessfulRevenue();
    }
}
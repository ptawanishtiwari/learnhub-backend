package com.example.parth.service;

import com.example.parth.entity.Course;
import com.example.parth.entity.Enrollment;
import com.example.parth.entity.Payment;
import com.example.parth.repository.CourseRepository;
import com.example.parth.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final CourseRepository courseRepository;

    private final EnrollmentService enrollmentService;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public PaymentService(
            PaymentRepository paymentRepository,
            CourseRepository courseRepository,
            EnrollmentService enrollmentService) {

        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentService = enrollmentService;
    }


    // =========================================================
    // 1. CREATE RAZORPAY ORDER
    // =========================================================

    public JSONObject createOrder(int customerId, int courseId) {

        try {

            // -------------------------------------------------
            // Find course
            // -------------------------------------------------

            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Course not found with id: " + courseId
                            )
                    );


            // -------------------------------------------------
            // Get course price
            // -------------------------------------------------

            double coursePrice = course.getPrice();

            if (coursePrice <= 0) {

                throw new RuntimeException(
                        "Course price must be greater than 0"
                );
            }


            // -------------------------------------------------
            // Convert rupees to paise
            // -------------------------------------------------

            int amountInPaise =
                    (int) Math.round(coursePrice * 100);


            // -------------------------------------------------
            // Create Razorpay client
            // -------------------------------------------------

            RazorpayClient razorpayClient =
                    new RazorpayClient(
                            razorpayKeyId,
                            razorpayKeySecret
                    );


            // -------------------------------------------------
            // Create order request
            // -------------------------------------------------

            JSONObject orderRequest = new JSONObject();

            orderRequest.put(
                    "amount",
                    amountInPaise
            );

            orderRequest.put(
                    "currency",
                    "INR"
            );

            orderRequest.put(
                    "receipt",
                    "course_" +
                            courseId +
                            "_customer_" +
                            customerId +
                            "_" +
                            System.currentTimeMillis()
            );


            // -------------------------------------------------
            // Create Razorpay order
            // -------------------------------------------------

            Order order =
                    razorpayClient.orders.create(
                            orderRequest
                    );


            // -------------------------------------------------
            // Get Razorpay order ID
            // -------------------------------------------------

            String razorpayOrderId =
                    order.get("id").toString();


            // -------------------------------------------------
            // Prepare response
            // -------------------------------------------------

            JSONObject response =
                    new JSONObject();

            response.put(
                    "success",
                    true
            );

            response.put(
                    "orderId",
                    razorpayOrderId
            );

            response.put(
                    "amount",
                    coursePrice
            );

            response.put(
                    "amountInPaise",
                    amountInPaise
            );

            response.put(
                    "currency",
                    "INR"
            );

            response.put(
                    "customerId",
                    customerId
            );

            response.put(
                    "courseId",
                    courseId
            );

            response.put(
                    "courseTitle",
                    course.getTitle()
            );

            response.put(
                    "keyId",
                    razorpayKeyId
            );

            return response;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to create Razorpay order: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // 2. VERIFY PAYMENT
    // =========================================================

    public JSONObject verifyPayment(
            int customerId,
            int courseId,
            String razorpayOrderId,
            String razorpayPaymentId,
            String razorpaySignature) {

        try {

            // -------------------------------------------------
            // Check course
            // -------------------------------------------------

            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Course not found with id: " + courseId
                            )
                    );


            // -------------------------------------------------
            // Verify Razorpay signature
            // -------------------------------------------------

            JSONObject attributes =
                    new JSONObject();

            attributes.put(
                    "razorpay_order_id",
                    razorpayOrderId
            );

            attributes.put(
                    "razorpay_payment_id",
                    razorpayPaymentId
            );

            attributes.put(
                    "razorpay_signature",
                    razorpaySignature
            );


            boolean isValid =
                    Utils.verifyPaymentSignature(
                            attributes,
                            razorpayKeySecret
                    );


            // -------------------------------------------------
            // Invalid payment
            // -------------------------------------------------

            if (!isValid) {

                JSONObject response =
                        new JSONObject();

                response.put(
                        "success",
                        false
                );

                response.put(
                        "status",
                        "FAILED"
                );

                response.put(
                        "message",
                        "Payment verification failed"
                );

                return response;
            }


            // -------------------------------------------------
            // Check duplicate payment
            // -------------------------------------------------

            Optional<Payment> existingPayment =
                    paymentRepository
                            .findByRazorpayPaymentId(
                                    razorpayPaymentId
                            );


            if (existingPayment.isPresent()) {

                Payment payment =
                        existingPayment.get();

                JSONObject response =
                        new JSONObject();

                response.put(
                        "success",
                        true
                );

                response.put(
                        "status",
                        "SUCCESS"
                );

                response.put(
                        "message",
                        "Payment already verified"
                );

                response.put(
                        "paymentId",
                        payment.getId()
                );

                response.put(
                        "razorpayPaymentId",
                        payment.getRazorpayPaymentId()
                );

                response.put(
                        "razorpayOrderId",
                        payment.getRazorpayOrderId()
                );

                response.put(
                        "enrollmentId",
                        payment.getEnrollmentId()
                );

                return response;
            }


            // -------------------------------------------------
            // Find/Create Enrollment
            // -------------------------------------------------

            int enrollmentId = 0;

            List<Enrollment> customerEnrollments =
                    enrollmentService
                            .getEnrollmentsByCustomer(
                                    customerId
                            );


            for (Enrollment enrollment :
                    customerEnrollments) {

                if (enrollment.getCourseId() == courseId) {

                    enrollmentId =
                            enrollment.getId();

                    break;
                }
            }


            // -------------------------------------------------
            // Create enrollment if not exists
            // -------------------------------------------------

            if (enrollmentId == 0) {

                Enrollment enrollment =
                        new Enrollment();

                enrollment.setCustomerId(
                        customerId
                );

                enrollment.setCourseId(
                        courseId
                );

                enrollment.setStatus(
                        "ACTIVE"
                );


                Enrollment savedEnrollment =
                        enrollmentService
                                .createEnrollment(
                                        enrollment
                                );


                enrollmentId =
                        savedEnrollment.getId();
            }


            // -------------------------------------------------
            // Save Payment
            // -------------------------------------------------

            Payment payment =
                    new Payment();

            payment.setCustomerId(
                    customerId
            );

            payment.setCourseId(
                    courseId
            );

            payment.setEnrollmentId(
                    enrollmentId
            );

            payment.setRazorpayOrderId(
                    razorpayOrderId
            );

            payment.setRazorpayPaymentId(
                    razorpayPaymentId
            );

            payment.setRazorpaySignature(
                    razorpaySignature
            );

            payment.setAmount(
                    course.getPrice()
            );

            payment.setCurrency(
                    "INR"
            );

            payment.setStatus(
                    "SUCCESS"
            );

            payment.setPaymentDate(
                    LocalDateTime.now()
            );


            Payment savedPayment =
                    paymentRepository.save(
                            payment
                    );


            // -------------------------------------------------
            // Success Response
            // -------------------------------------------------

            JSONObject response =
                    new JSONObject();

            response.put(
                    "success",
                    true
            );

            response.put(
                    "status",
                    "SUCCESS"
            );

            response.put(
                    "message",
                    "Payment verified successfully"
            );

            response.put(
                    "paymentId",
                    savedPayment.getId()
            );

            response.put(
                    "razorpayPaymentId",
                    razorpayPaymentId
            );

            response.put(
                    "razorpayOrderId",
                    razorpayOrderId
            );

            response.put(
                    "customerId",
                    customerId
            );

            response.put(
                    "courseId",
                    courseId
            );

            response.put(
                    "courseTitle",
                    course.getTitle()
            );

            response.put(
                    "amount",
                    course.getPrice()
            );

            response.put(
                    "currency",
                    "INR"
            );

            response.put(
                    "enrollmentId",
                    enrollmentId
            );

            return response;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Payment verification failed: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // 3. GET ALL PAYMENTS
    // =========================================================

    public List<Payment> getAllPayments() {

        return paymentRepository.findAll();
    }


    // =========================================================
    // 4. GET PAYMENT BY ID
    // =========================================================

    public Payment getPaymentById(int id) {

        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found with id: " + id
                        )
                );
    }


    // =========================================================
    // 5. GET PAYMENTS BY CUSTOMER
    // =========================================================

    public List<Payment> getPaymentsByCustomer(
            int customerId) {

        return paymentRepository
                .findByCustomerId(customerId);
    }


    // =========================================================
    // 6. GET PAYMENTS BY COURSE
    // =========================================================

    public List<Payment> getPaymentsByCourse(
            int courseId) {

        return paymentRepository
                .findByCourseId(courseId);
    }


    // =========================================================
    // 7. GET PAYMENT BY RAZORPAY ORDER ID
    // =========================================================

    public Payment getPaymentByOrderId(
            String orderId) {

        return paymentRepository
                .findByRazorpayOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found for Razorpay order: "
                                        + orderId
                        )
                );
    }


    // =========================================================
    // 8. GET PAYMENT BY RAZORPAY PAYMENT ID
    // =========================================================

    public Payment getPaymentByRazorpayPaymentId(
            String paymentId) {

        return paymentRepository
                .findByRazorpayPaymentId(paymentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found for Razorpay payment: "
                                        + paymentId
                        )
                );
    }


    // =========================================================
    // 9. GET PAYMENTS BY STATUS
    // =========================================================

    public List<Payment> getPaymentsByStatus(
            String status) {

        return paymentRepository
                .findByStatus(status.toUpperCase());
    }


    // =========================================================
    // 10. GET SUCCESSFUL PAYMENTS
    // =========================================================

    public List<Payment> getSuccessfulPayments() {

        return paymentRepository
                .findByStatus("SUCCESS");
    }


    // =========================================================
    // 11. GET PENDING PAYMENTS
    // =========================================================

    public List<Payment> getPendingPayments() {

        return paymentRepository
                .findByStatus("PENDING");
    }


    // =========================================================
    // 12. GET FAILED PAYMENTS
    // =========================================================

    public List<Payment> getFailedPayments() {

        return paymentRepository
                .findByStatus("FAILED");
    }


    // =========================================================
    // 13. GET TOTAL PAYMENT COUNT
    // =========================================================

    public long getPaymentCount() {

        return paymentRepository.count();
    }


    // =========================================================
    // 14. GET SUCCESSFUL PAYMENT COUNT
    // =========================================================

    public long getSuccessfulPaymentCount() {

        return paymentRepository
                .countByStatus("SUCCESS");
    }


    // =========================================================
    // 15. GET PENDING PAYMENT COUNT
    // =========================================================

    public long getPendingPaymentCount() {

        return paymentRepository
                .countByStatus("PENDING");
    }


    // =========================================================
    // 16. GET FAILED PAYMENT COUNT
    // =========================================================

    public long getFailedPaymentCount() {

        return paymentRepository
                .countByStatus("FAILED");
    }


    // =========================================================
    // 17. GET TOTAL SUCCESSFUL REVENUE
    // =========================================================

    public Double getTotalSuccessfulRevenue() {

        Double revenue =
                paymentRepository
                        .getTotalAmountByStatus("SUCCESS");

        if (revenue == null) {
            return 0.0;
        }

        return revenue;
    }
}
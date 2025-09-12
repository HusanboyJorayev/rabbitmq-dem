package org.example.rabbitmq;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentProducer paymentProducer;

    public PaymentController(PaymentProducer paymentProducer) {
        this.paymentProducer = paymentProducer;
    }

    // To'lovni boshlash
    @PostMapping("/{paymentId}")
    public ResponseEntity<String> makePayment(@PathVariable String paymentId) {
        paymentProducer.sendPaymentRequest(paymentId);
        return ResponseEntity.ok("✅ Payment request sent for ID: " + paymentId);
    }
}

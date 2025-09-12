package org.example.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    private final RabbitTemplate rabbitTemplate;

    private int attemptCount = 0;

    public PaymentConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitConfig.REQUEST_QUEUE)
    public void consume(String paymentId) {
        System.out.println("🔔 Received payment: " + paymentId + " | attempt=" + attemptCount);

        try {
            attemptCount++;

            // Simulyatsiya: 3-urinishgacha xato, keyin success
            if (attemptCount < 3) {
                throw new RuntimeException("Bank API timeout");
            }

            // Agar 3-urinishda ishlasa
            System.out.println("✅ Payment success: " + paymentId);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());

            // Retry chain logikasi
            if (attemptCount == 1) {
                rabbitTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE,
                        RabbitConfig.RETRY_5S_QUEUE, paymentId);
            } else if (attemptCount == 2) {
                rabbitTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE,
                        RabbitConfig.RETRY_30S_QUEUE, paymentId);
            } else if (attemptCount == 3) {
                rabbitTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE,
                        RabbitConfig.RETRY_2M_QUEUE, paymentId);
            } else {
                // Retry limit tugasa → DLQ
                rabbitTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE,
                        RabbitConfig.DLQ, paymentId);
                System.out.println("🚨 Sent to DLQ: " + paymentId);
            }
        }
    }
}

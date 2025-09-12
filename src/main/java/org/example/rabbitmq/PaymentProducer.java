package org.example.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    private final RabbitTemplate rabbitTemplate;

    public PaymentProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPaymentRequest(String paymentId) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.MAIN_EXCHANGE,
                RabbitConfig.REQUEST_QUEUE,
                paymentId
        );
        System.out.println("✅ Payment request sent: " + paymentId);
    }
}

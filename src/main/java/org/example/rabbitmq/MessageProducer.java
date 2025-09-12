package org.example.rabbitmq;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate amqpTemplate;

    public void send(String message) {
        amqpTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE, RabbitConfig.REQUEST_QUEUE, message);
        System.out.println("Message sent: " + message);
    }
}


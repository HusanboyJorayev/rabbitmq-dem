package org.example.rabbitmq;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

//    @RabbitListener(queues = RabbitMQConfig.QUEUE)
//    public void receive(String message) {
//        System.out.println("Received message: " + message);
//    }
}


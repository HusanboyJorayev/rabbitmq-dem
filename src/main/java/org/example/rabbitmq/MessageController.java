package org.example.rabbitmq;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageProducer producer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        producer.send(message);
        return "Message sent: " + message;
    }

    @GetMapping("/api/read-message")
    public String readMessage() {
        // Xabarni o‘qiydi va avtomatik queue’dan o‘chadi
        String msg = (String) rabbitTemplate.receiveAndConvert("demo-queue");
        return msg;
    }
}


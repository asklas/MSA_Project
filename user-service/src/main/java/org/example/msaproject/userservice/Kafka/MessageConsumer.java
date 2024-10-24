package org.example.msaproject.userservice.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "user", groupId = "org-example-msaProject")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}


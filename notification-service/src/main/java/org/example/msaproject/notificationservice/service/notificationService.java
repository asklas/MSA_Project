package org.example.msaproject.notificationservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class notificationService {
    @KafkaListener(topics = "msa_user", groupId = "user_group")
    public void listen(String string) {
        System.out.println("Received string: " + string);
        // 추가적인 비즈니스 로직을 여기에 작성
    }
}


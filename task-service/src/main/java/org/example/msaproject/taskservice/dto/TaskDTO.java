package org.example.msaproject.taskservice.dto;

import lombok.*;

import java.time.LocalDateTime;

public class TaskDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequestDto {
        private String title;
        private String description;
        private String status;
        private String priority;
        private Long userId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDto {
        private Long id;
        private String title;
        private String description;
        private String status;
        private String priority;
        private Long userId;
        private LocalDateTime createdAt;
        private LocalDateTime dueDate;
    }
}


package org.example.msaproject.taskservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status; // TODO, IN_PROGRESS, DONE
    private String priority; // LOW, MEDIUM, HIGH
    private Long userId; // Creator's user ID
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
}


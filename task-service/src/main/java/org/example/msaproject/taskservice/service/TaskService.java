package org.example.msaproject.taskservice.service;


import org.example.msaproject.taskservice.dto.TaskDTO;
import org.example.msaproject.taskservice.entity.Task;
import org.example.msaproject.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskDTO.ResponseDto createTask(TaskDTO.CreateRequestDto requestDto) {
        Task task = Task.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .status(requestDto.getStatus())
                .priority(requestDto.getPriority())
                .userId(requestDto.getUserId())
                .createdAt(LocalDateTime.now())
                .dueDate(LocalDateTime.now())
                .build();

        taskRepository.save(task);

        return new TaskDTO.ResponseDto(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(),
                task.getPriority(), task.getUserId(), task.getCreatedAt(), task.getDueDate());
    }

    public List<TaskDTO.ResponseDto> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(task -> new TaskDTO.ResponseDto(task.getId(), task.getTitle(), task.getDescription(),
                        task.getStatus(), task.getPriority(), task.getUserId(), task.getCreatedAt(), task.getDueDate()))
                .collect(Collectors.toList());
    }
}

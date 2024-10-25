package org.example.msaproject.taskservice.controller;

import org.example.msaproject.taskservice.dto.TaskDTO;
import org.example.msaproject.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO.ResponseDto> createTask(@RequestBody TaskDTO.CreateRequestDto requestDto) {
        TaskDTO.ResponseDto createdTask = taskService.createTask(requestDto);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO.ResponseDto>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskDTO.ResponseDto> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
}


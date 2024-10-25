package org.example.msaproject.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.msaproject.userservice.dto.UserDTO;
import org.example.msaproject.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final KafkaTemplate<String, UserDTO> kafkaTemplate;
    private final UserService userService;

    private static final String TOPIC = "user";


    @PostMapping("/register")
    public ResponseEntity<UserDTO.CreateResponseDto> sendMessage(@RequestBody UserDTO.CreateRequestDto user) {
        UserDTO.CreateResponseDto createResponseDto = userService.register(user);
        return ResponseEntity.ok(createResponseDto);
    }
    @PutMapping("/")
    public ResponseEntity<UserDTO.Update> modify(@Validated @RequestBody UserDTO.Update dto) {
        return ResponseEntity.ok(userService.update(dto));
    }
}


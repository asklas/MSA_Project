package org.example.msaproject.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.msaproject.userservice.dto.UserDTO;
import org.example.msaproject.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    private static final String TOPIC = "user";


    @PostMapping("/register")
    public ResponseEntity<UserDTO.CreateResponseDto> sendMessage(@RequestBody UserDTO.CreateRequestDto user) {
        UserDTO.CreateResponseDto createResponseDto = userService.register(user);
        return ResponseEntity.ok(createResponseDto);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<UserDTO.LoginResponseDto> login(@Validated @RequestBody UserDTO.LoginRequestDTO request) {
        // 인증 성공
        UserDTO.LoginResponseDto responseDto = userService.checkLoginIdAndPassword(request.getUserId(), request.getPassword());

        Long id = responseDto.getId();
        String userId = responseDto.getUserId();

        String accessToken = userService.generateAccessToken(id, userId);
        String refreshToken = userService.generateRefreshToken(id, userId);

        userService.setRefreshToken(id, refreshToken);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .body(responseDto);
    }


    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestBody String token) {
        return ResponseEntity.ok(userService.refreshAccessToken(token));
    }


    @PutMapping("/update")
    public ResponseEntity<UserDTO.UpdateDTO> modify(@Validated @RequestBody UserDTO.UpdateDTO dto) {
        return ResponseEntity.ok(userService.update(dto));
    }
}


package org.example.msaproject.userservice.service;

import lombok.RequiredArgsConstructor;
import org.example.msaproject.userservice.dto.UserDTO;
import org.example.msaproject.userservice.entity.User;
import org.example.msaproject.userservice.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    @KafkaListener(topics = "user", groupId = "org-example-msaProject")

    @Transactional
    public UserDTO.CreateResponseDto register(UserDTO.CreateRequestDto dto) {
        try {
            //기존의 회원이 있는지 검사
            Optional<User> user = userRepository.findByUsername(dto.getUsername());
            if (user.isPresent()) {
                throw new RuntimeException("해당 아이디로 회원가입한 회원이 존재합니다");
            }

            String password = dto.getPassword();
            dto.setPassword(passwordEncoder.encode(password));

            User savedUser = userRepository.save(dto.toEntity());
            return new UserDTO.CreateResponseDto("회원가입이 완료되었습니다");
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public UserDTO.Update update(UserDTO.Update dto) {
        Optional<User> userOptional = userRepository.findByUsername(dto.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(dto.getUsername());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            userRepository.save(user);

            return new UserDTO.Update(
                    user.getUsername(),
                    user.getPassword()// 잠시 수정
            );
        } else {
            return null;
        }
    }
}



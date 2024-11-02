package org.example.msaproject.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.example.msaproject.userservice.config.KafkaProducerConfig;
import org.example.msaproject.userservice.dto.UserDTO;
import org.example.msaproject.userservice.entity.Users;
import org.example.msaproject.userservice.repository.UserRepository;
import org.example.msaproject.userservice.util.JwtUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final KafkaProducerConfig kafkaTemplate;
//    @KafkaListener(topics = "user", groupId = "org-example-msaProject")

    @Transactional
    public UserDTO.CreateResponseDto register(UserDTO.CreateRequestDto dto) {
        try {
            if (userRepository.existsByUserId(dto.getUserId())) {
                throw new RuntimeException("해당 아이디로 회원가입한 회원이 존재합니다");
            }

            String password = dto.getPassword();
            dto.setPassword(passwordEncoder.encode(password));
            dto.setRole("ROLE_USER");

            Users savedUsers = userRepository.save(dto.toEntity());
            kafkaTemplate.sendMessage("msa_user", savedUsers);
            return new UserDTO.CreateResponseDto("회원가입이 완료되었습니다");
        } catch (Exception e) {
            return null;
        }
    }

    public UserDTO.LoginResponseDto checkLoginIdAndPassword(String userId, String pw) {
        Optional<Users> opUser = userRepository.findByUserId(userId);
        log.info("Attempting login with userId: {}, password: {}", userId, pw);

        if (opUser.isEmpty()) {
            throw new RuntimeException();
        }

        if (!passwordEncoder.matches(pw, opUser.get().getPassword())) {
            throw new RuntimeException();
        }

        Users users = opUser.get();
        UserDTO.LoginResponseDto responseDto = new UserDTO.LoginResponseDto(users);
        kafkaTemplate.sendMessage("msa_user", responseDto);
        return responseDto;
    }
    @Transactional
    public void setRefreshToken(Long id, String refreshToken) {
        Users users = userRepository.findById(id).get();
        users.updateRefreshToken(refreshToken);

    }
    public String generateAccessToken(Long id, String userId) {
        List<String> authorities;
        if (userId.equals("admin")) {
            authorities = List.of("ROLE_ADMIN");
        } else {
            authorities = List.of("ROLE_MEMBER");
        }

        return jwtUtil.encodeAccessToken(15,
                Map.of("id", id.toString(),
                        "userId", userId,
                        "authorities", authorities)
        );
    }
    public String generateRefreshToken(Long id, String userId) {
        return jwtUtil.encodeRefreshToken(60 * 24 * 3,
                Map.of("id", id.toString(),
                        "userId", userId)
        );

    }
    public String refreshAccessToken(String refreshToken) {
        //화이트리스트 처리
        Users users = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token expired"));

        //리프레시 토큰이 만료되었다면 로그아웃
        try {
            Claims claims = jwtUtil.decode(refreshToken);
        } catch (ExpiredJwtException e) {
            // 클라이언트한테 만료되었다고 알려주기
            throw new RuntimeException("Refresh token expired");

        }
        return generateAccessToken(users.getId(), users.getUserId());
    }

    @Transactional
    public UserDTO.UpdateDTO update(UserDTO.UpdateDTO dto) {
        Optional<Users> userOptional = userRepository.findByUserId(dto.getUserId());

        if (userOptional.isPresent()) {
            Users users = userOptional.get();
            users.setUsername(dto.getUsername());
            users.setUserId(dto.getUserId());
            users.setPassword(passwordEncoder.encode(dto.getPassword()));
            users.setEmail(dto.getEmail());
            userRepository.save(users);

            return new UserDTO.UpdateDTO(
                    users.getUsername(),
                    users.getUserId(),
                    users.getPassword(),
                    users.getEmail()
            );
        } else {
            return null;
        }
    }
}



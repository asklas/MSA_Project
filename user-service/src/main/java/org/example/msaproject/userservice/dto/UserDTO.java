package org.example.msaproject.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.msaproject.userservice.entity.Users;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Builder
public class UserDTO {

    @Data
    @AllArgsConstructor
    public static class CreateRequestDto {
        private String username;
        private String userId;
        private String password;
        private String email;
        private String role;


        public Users toEntity() {
            return Users.builder()
                    .username(username)
                    .userId(userId)
                    .password(password)
                    .email(email)
                    .role(role)
                    .build();
        }
    }
    @Data
    public static class CreateResponseDto {
        private String message;

        public CreateResponseDto( String message) {
            this.message=message;
        }
    }

    @Data
    public static class LoginRequestDTO {
        private String userId;
        private String password;
    }
    @Data
    public static class LoginResponseDto {
        private long id;
        private String userId;
        private String username;


        public LoginResponseDto(Users users) {
            this.id = users.getId();
            this.userId = users.getUserId();
            this.username = users.getUsername();
        }
    }

    @Data
    @AllArgsConstructor
    public static class UpdateDTO {
        private String username;
        private String userId;
        private String password;
        private String email;
    }
}

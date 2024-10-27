package org.example.msaproject.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.msaproject.userservice.entity.Users;

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


        public Users toEntity() {
            return Users.builder()
                    .username(username)
                    .userId(userId)
                    .password(password)
                    .email(email)
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
        private String accessToken;
        private String refreshToken;

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

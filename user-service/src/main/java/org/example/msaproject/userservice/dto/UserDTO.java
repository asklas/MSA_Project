package org.example.msaproject.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.msaproject.userservice.entity.User;

@Getter
@Builder
public class UserDTO {

    @Data
    @AllArgsConstructor
    public static class CreateRequestDto {
        private String username;
        private String password;


        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
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
    @AllArgsConstructor
    public static class Update {
        private String username;
        private String password;
    }
}

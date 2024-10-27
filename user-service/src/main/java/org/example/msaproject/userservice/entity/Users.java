package org.example.msaproject.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String userId;
    private String password;
    private String email;
    @Column(columnDefinition = "TEXT")
    private String refreshToken;


    @Builder
    public Users(String username, String userId, String password, String email) {
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

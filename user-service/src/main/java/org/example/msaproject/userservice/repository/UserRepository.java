package org.example.msaproject.userservice.repository;

import org.example.msaproject.userservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Boolean existsByUserId(String userId);
    Optional<Users> findByUserId(String userId);
    Optional<Users> findByRefreshToken(String refreshToken);
}

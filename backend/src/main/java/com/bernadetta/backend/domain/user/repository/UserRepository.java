package com.bernadetta.backend.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bernadetta.backend.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    boolean existsByCallNumber(String callNumber);

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByCallNumber(String callNumber);
}

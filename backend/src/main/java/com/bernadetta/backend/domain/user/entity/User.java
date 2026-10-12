package com.bernadetta.backend.domain.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String userId;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String callNumber;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 15)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", updatable = true)
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserStatus status;

    @Builder
    public User(
            Long id,
            String userId,
            String email,
            String callNumber,
            String password,
            String name,
            String nickname,
            Role role,
            UserStatus status

    ) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.callNumber = callNumber;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role == null ? Role.USER : role;
        this.status = status == null ? UserStatus.ACTIVE : status;
    }

    public void update(String nickname, String callNumber) {
        this.nickname = nickname;
        this.callNumber = callNumber;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}

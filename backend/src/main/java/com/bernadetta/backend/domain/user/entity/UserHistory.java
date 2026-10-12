package com.bernadetta.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "user_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HistoryType historyType;

    @Column(nullable = false, length = 255)
    private String oldValue;

    @Column(nullable = false, length = 255)
    private String newValue;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public UserHistory(Long userId, HistoryType historyType, String oldValue, String newValue) {
        this.userId = userId;
        this.historyType = historyType;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}

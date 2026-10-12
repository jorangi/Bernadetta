package com.bernadetta.backend.domain.user.dto.response;

import java.time.LocalDateTime;

import com.bernadetta.backend.domain.user.entity.Role;
import com.bernadetta.backend.domain.user.entity.User;

public record UserResponse(
        Long id,
        String userId,
        String email,
        String name,
        String nickname,
        String callNumber,
        Role role,
        LocalDateTime createdAt) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getNickname(),
                user.getCallNumber(),
                user.getRole(),
                user.getCreatedAt());
    }
}

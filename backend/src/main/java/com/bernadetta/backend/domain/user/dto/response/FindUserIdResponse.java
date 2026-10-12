package com.bernadetta.backend.domain.user.dto.response;

import java.time.LocalDateTime;

public record FindUserIdResponse(
        String userId,
        LocalDateTime createdAt) {
}

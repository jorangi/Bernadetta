package com.bernadetta.backend.domain.auth.dto.response;

import com.bernadetta.backend.domain.user.dto.response.UserResponse;

public record LoginResponse(
                String accessToken,
                UserResponse user) {
}

package com.bernadetta.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FindUserIdRequest(
        @NotBlank(message = "이름을 입력해주세요.") String name,
        @NotBlank(message = "이메일을 입력해주세요.") String email) {
}

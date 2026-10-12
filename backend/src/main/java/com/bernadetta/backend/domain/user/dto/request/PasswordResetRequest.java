package com.bernadetta.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequest(
        @NotBlank(message = "아이디를 입력해주세요.") String userId,
        @NotBlank(message = "이메일을 입력해주세요.") String email,
        @NotBlank(message = "새 비밀번호를 입력해주세요.") String newPassword,
        @NotBlank(message = "비밀번호 확인을 입력해주세요.") String newPasswordConfirm) {
}

package com.bernadetta.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdatePasswordRequest(
        @NotBlank(message = "기존 비밀번호를 입력해주세요") String currentPassword,
        @NotBlank(message = "새로운 비밀번호를 입력해주세요") String newPassword,
        @NotBlank(message = "새로운 비밀번호를 확인해주세요") String newPasswordConfirm) {
}
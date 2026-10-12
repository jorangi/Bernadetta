package com.bernadetta.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
                @NotBlank(message = "닉네임은 필수입니다.") String nickName,
                @NotBlank(message = "전화번호는 필수입니다.") String callNumber) {
}

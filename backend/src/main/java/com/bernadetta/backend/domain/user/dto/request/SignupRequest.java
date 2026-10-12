package com.bernadetta.backend.domain.user.dto.request;

import jakarta.validation.constraints.*;

public record SignupRequest(
        @NotBlank(message = "이메일은 필수 입력값입니다.") @Email(message = "올바른 이메일 형식이 아닙니다.") String email,
        @NotBlank(message = "아이디는 필수 입력값입니다.") @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하여야 합니다.") String userId,
        @NotBlank(message = "전화번호는 필수 입력값입니다.") @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 숫자 10~11자리로 입력해주세요.") String callNumber,
        @NotBlank(message = "이름은 필수 입력값입니다.") String name,
        @NotBlank(message = "비밀번호는 필수 입력값입니다.") @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.") String password,
        @NotBlank(message = "닉네임은 필수 입력값입니다.") String nickName) {
}

package com.bernadetta.backend.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernadetta.backend.domain.auth.dto.request.LoginRequest;
import com.bernadetta.backend.domain.auth.dto.response.LoginResponse;
import com.bernadetta.backend.domain.auth.jwt.JwtTokenProvider;
import com.bernadetta.backend.domain.user.dto.response.UserResponse;
import com.bernadetta.backend.domain.user.entity.User;
import com.bernadetta.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider JwTTokenProvider;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다."));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
        String accessToken = JwTTokenProvider.createToken(user.getId(), user.getUserId(), user.getPassword());
        return new LoginResponse(accessToken, UserResponse.from(user));
    }

    public void logout(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
        }
    }
}

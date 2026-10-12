package com.bernadetta.backend.domain.user.service;

import com.bernadetta.backend.domain.user.repository.UserHistoryRepository;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernadetta.backend.domain.user.dto.request.FindUserIdRequest;
import com.bernadetta.backend.domain.user.dto.request.PasswordResetRequest;
import com.bernadetta.backend.domain.user.dto.request.SignupRequest;
import com.bernadetta.backend.domain.user.dto.request.UserUpdatePasswordRequest;
import com.bernadetta.backend.domain.user.dto.request.UserUpdateRequest;
import com.bernadetta.backend.domain.user.dto.response.FindUserIdResponse;
import com.bernadetta.backend.domain.user.dto.response.UserResponse;
import com.bernadetta.backend.domain.user.entity.HistoryType;
import com.bernadetta.backend.domain.user.entity.Role;
import com.bernadetta.backend.domain.user.entity.User;
import com.bernadetta.backend.domain.user.entity.UserHistory;
import com.bernadetta.backend.domain.user.entity.UserStatus;
import com.bernadetta.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserHistoryRepository userHistoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signup(SignupRequest request) {
        if (userRepository.existsByUserId(request.userId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByCallNumber(request.callNumber())) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .userId(request.userId())
                .email(request.email())
                .callNumber(request.callNumber())
                .name(request.name())
                .nickname(request.nickName())
                .password(encodedPassword)
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);
    }

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!user.getCallNumber().equals(request.callNumber())
                && userRepository.existsByCallNumber(request.callNumber())) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
        }
        recordHistory(id, HistoryType.CALLNUMBER_CHANGE, user.getCallNumber(), request.callNumber());
        recordHistory(id, HistoryType.NICKNAME_CHANGE, user.getNickname(), request.nickName());
        user.update(request.nickName(), request.callNumber());
        return UserResponse.from(user);
    }

    private void recordHistory(Long userId, HistoryType type, String oldValue, String newValue) {
        if (!Objects.equals(oldValue, newValue)) {
            userHistoryRepository.save(UserHistory.builder()
                    .userId(userId)
                    .historyType(type)
                    .oldValue(oldValue)
                    .newValue(newValue)
                    .build());
        }
    }

    @Transactional
    public UserResponse updatePassword(Long id, UserUpdatePasswordRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
        if (!request.newPassword().equals(request.newPasswordConfirm())) {
            throw new IllegalArgumentException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }
        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.newPassword());
        recordHistory(id, HistoryType.PASSWORD_CHANGE, user.getPassword(), encodedPassword);

        user.updatePassword(encodedPassword);
        return UserResponse.from(user);
    }

    @Transactional
    public void resetPassword(PasswordResetRequest request) {
        User user = userRepository.findByUserId(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (!user.getEmail().equals(request.email())) {
            throw new IllegalArgumentException("아이디와 이메일 정보가 일치하지 않습니다.");
        }
        if (!request.newPassword().equals(request.newPasswordConfirm())) {
            throw new IllegalArgumentException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }
        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.newPassword());
        recordHistory(user.getId(), HistoryType.PASSWORD_CHANGE, user.getPassword(), encodedPassword);
        user.updatePassword(encodedPassword);
    }

    public FindUserIdResponse findUserId(FindUserIdRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        if (!user.getName().equals(request.name())) {
            throw new IllegalArgumentException("이름이 일치하지 않습니다.");
        }
        FindUserIdResponse response = new FindUserIdResponse(user.getUserId(), user.getCreatedAt());
        return response;
    }
}

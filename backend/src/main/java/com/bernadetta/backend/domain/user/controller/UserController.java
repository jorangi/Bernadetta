package com.bernadetta.backend.domain.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernadetta.backend.domain.auth.dto.response.LoginResponse;
import com.bernadetta.backend.domain.user.dto.request.FindUserIdRequest;
import com.bernadetta.backend.domain.user.dto.request.PasswordResetRequest;
import com.bernadetta.backend.domain.user.dto.request.SignupRequest;
import com.bernadetta.backend.domain.user.dto.request.UserUpdatePasswordRequest;
import com.bernadetta.backend.domain.user.dto.request.UserUpdateRequest;
import com.bernadetta.backend.domain.user.dto.response.FindUserIdResponse;
import com.bernadetta.backend.domain.user.dto.response.UserResponse;
import com.bernadetta.backend.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest request) {
        UserResponse response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        UserResponse response = userService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<UserResponse> updatePassword(@PathVariable Long id,
            @Valid @RequestBody UserUpdatePasswordRequest request) {
        UserResponse response = userService.updatePassword(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody PasswordResetRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/find-id")
    public ResponseEntity<FindUserIdResponse> findUserId(@Valid @RequestBody FindUserIdRequest request) {
        FindUserIdResponse response = userService.findUserId(request);
        return ResponseEntity.ok(response);
    }
}

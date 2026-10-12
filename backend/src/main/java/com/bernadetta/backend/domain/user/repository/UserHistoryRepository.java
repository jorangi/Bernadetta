package com.bernadetta.backend.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernadetta.backend.domain.user.entity.UserHistory;

import java.util.List;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findByUserId(Long userId);
}

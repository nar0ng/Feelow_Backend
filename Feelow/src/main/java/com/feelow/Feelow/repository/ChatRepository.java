package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    // List<Chat> findByStudentStudentIdAndDate(Long studentId, String date);

    List<Chat> findByMemberMemberIdAndDate(Long memberId, String date);
}

package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.Chat;
import com.feelow.Feelow.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByMemberMemberIdAndDate(Long memberId, String date);
}

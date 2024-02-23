package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByMemberMemberIdAndDate(Long memberId, String date);


    List<Chat> findByMemberIdAndInputTimeBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate);

    List<Chat> findByMemberMemberIdAndLocalDateBetween(Long memberId, LocalDate startDate, LocalDate endDate);
}

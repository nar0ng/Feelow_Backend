package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberById(Long id);

    Optional<Member> findByMemberId(Long memberId);



}

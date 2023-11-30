package com.feelow.Feelow.repository;

import com.feelow.Feelow.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}

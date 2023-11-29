package com.feelow.Feelow.repository;

import com.feelow.Feelow.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}

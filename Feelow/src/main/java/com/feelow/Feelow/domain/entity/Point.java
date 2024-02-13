package com.feelow.Feelow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Point")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @Column(nullable = false)
    private Long conversationCount; // 대화횟수

    @Column(nullable = false)
    private Long attendanceCheck; // 출석체크

}

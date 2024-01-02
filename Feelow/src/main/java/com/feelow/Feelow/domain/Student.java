package com.feelow.Feelow.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Student")
@Table(name="Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("student_number")
    private int student_number;

    @JsonProperty("name")
    private String student_name;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = true, updatable = false)
    private Member member;

    @ManyToOne(optional = false)
    @JoinColumn(name = "classroom_id", referencedColumnName = "classroom_id", insertable = true, updatable = false)
    private Classroom classroom;

}

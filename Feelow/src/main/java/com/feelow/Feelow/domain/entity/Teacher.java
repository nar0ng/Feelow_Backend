package com.feelow.Feelow.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Entity(name="Teacher")
@Table(name="Teacher")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long teacherId;

    @JsonProperty("name")
    private String teacherName;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = true, updatable = false)
    private Member member;

    @OneToOne
    @JoinColumn(name = "classroom_id", referencedColumnName = "classroom_id", insertable = true, updatable = false)
    private Classroom classroom;
}

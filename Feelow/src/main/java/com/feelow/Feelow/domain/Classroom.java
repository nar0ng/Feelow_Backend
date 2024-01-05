package com.feelow.Feelow.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Classroom")
@Table(name = "classroom")
public class Classroom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classroom_id")
    private Long classroomId;

    @JsonProperty("school")
    private String school;

    @JsonProperty("grade")
    private int grade;

    @JsonProperty("class_num")
    @Column(name = "class_num")
    private int classNum;

    // 여러 명의 학생을 가짐
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    private List<Student> students;

    // 한 명의 선생님을 가짐
    @OneToOne(mappedBy = "classroom", cascade = CascadeType.ALL)
    private Teacher teacher;
}
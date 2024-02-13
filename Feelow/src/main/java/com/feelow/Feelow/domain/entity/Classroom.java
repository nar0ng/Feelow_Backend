package com.feelow.Feelow.domain.entity;

import com.fasterxml.jackson.annotation.*;
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
@Table(name = "Classroom")
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
    private int classNum;


    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Student> students;

    @OneToOne(mappedBy = "classroom", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Teacher teacher;
}

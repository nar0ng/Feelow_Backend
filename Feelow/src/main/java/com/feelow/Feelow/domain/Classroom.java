package com.feelow.Feelow.domain;

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
@Entity(name="Classroom")
@Table(name="Classroom")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroom_id;

    @JsonProperty("school")
    private String school;

    @JsonProperty("grade")
    private int grade;

    @JsonProperty("class_num")
    private int class_num;



}

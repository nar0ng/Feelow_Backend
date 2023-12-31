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
@Entity(name="Teacher")
@Table(name="Teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long teacher_id;


    @JsonProperty("name")
    private String teacher_name;

    private Long classroom_id;

    @JsonProperty("member_type")
    private String member_type;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "classroom_id", referencedColumnName = "classroom_id", insertable = false, updatable = false)
    private Classroom classroom;
}

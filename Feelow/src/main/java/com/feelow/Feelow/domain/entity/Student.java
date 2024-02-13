package com.feelow.Feelow.domain.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Student")
@Table(name="Student")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("student_number")
    private int studentNumber;

    @JsonProperty("name")
    private String studentName;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = true, updatable = false)
    private Member member;

    @ManyToOne(optional = false)
    @JsonBackReference
    @JoinColumn(name = "classroom_id", referencedColumnName = "classroom_id", insertable = true, updatable = true)
    private Classroom classroom;

    @Column(name = "character_image_path")
    private String CharacterImagePath;
}

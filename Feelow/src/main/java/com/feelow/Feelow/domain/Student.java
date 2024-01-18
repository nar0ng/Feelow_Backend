package com.feelow.Feelow.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private int studentNumber;

    @JsonProperty("name")
    private String studentName;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = true, updatable = false)
    private Member member;

    @ManyToOne(optional = false)
    @JsonBackReference
    @JoinColumn(name = "classroom_id", referencedColumnName = "classroom_id", insertable = true, updatable = true)
    private Classroom classroom;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Chat> chats;

}

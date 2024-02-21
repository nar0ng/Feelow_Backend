package com.feelow.Feelow.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Chat")
@Table(name = "Chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    private String date;

    private String input;

    @Column(name = "response", length = 500)
    private String response;

    @Column(name = "positive_score")
    private Double positiveScore;

    @Column(name = "conversation_count")
    private int conversationCount;

    @Column(name = "input_time")
    private LocalDateTime inputTime;

    @Column(name = "history_sum")
    private String historySum;

    @Column(name = "today_sentence")
    private String todaySentence;

    @ManyToOne(optional = false)
    @JsonBackReference
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = true, updatable = false)
    private Member member;

    @PrePersist
    @PreUpdate
    public void prePersistAndUpdate() {
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}

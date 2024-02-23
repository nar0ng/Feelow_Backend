package com.feelow.Feelow.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDto {
    private LocalDate localDate;
    private String historySum;
    private String todaySentence;

}

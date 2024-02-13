package com.feelow.Feelow.dto;

import com.feelow.Feelow.entity.Point;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointDto {

    private Long pointId;
    private Long conversationCount; // 대화횟수
    private Long attendanceCheck;

    public Point toEntity() {return new Point(pointId, conversationCount, attendanceCheck);}
}

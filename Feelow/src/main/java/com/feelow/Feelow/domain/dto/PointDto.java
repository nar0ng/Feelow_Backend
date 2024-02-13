package com.feelow.Feelow.domain.dto;

import com.feelow.Feelow.domain.entity.Point;
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

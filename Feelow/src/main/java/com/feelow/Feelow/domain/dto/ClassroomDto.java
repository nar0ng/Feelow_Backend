package com.feelow.Feelow.domain.dto;
        import com.feelow.Feelow.domain.entity.Student;
        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDto {
    private Long classroomId;
    private String school;
    private int grade;
    private int classNum;
    private List<Student> students;
}

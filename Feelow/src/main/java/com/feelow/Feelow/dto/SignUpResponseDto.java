package com.feelow.Feelow.dto;

        import com.feelow.Feelow.domain.Member;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {
    private String token;
    private int exprTime;
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }
}

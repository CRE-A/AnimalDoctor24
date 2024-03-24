package kr.itycoon.plutoid.biz.member.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDeleteRequest {
    @NonNull
    private String memberId;

    @NonNull
    private String email;
}

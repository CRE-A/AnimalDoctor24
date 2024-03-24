package kr.itycoon.plutoid.biz.member.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class MemberUpdateRequest {
    @NonNull
    private String memberId;

    @NonNull
    private String memberName;

    @NonNull
    private String photoUrl;
}

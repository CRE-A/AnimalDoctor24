package kr.itycoon.plutoid.biz.member.dto;

import kr.itycoon.plutoid.biz.member.domain.Member;
import kr.itycoon.plutoid.biz.member.domain.Setting;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class MemberSettingResponse {
    
    @NonNull
    private Member member;

    @NonNull
    private Setting setting;

    @Builder
    public MemberSettingResponse(Member member, Setting setting) {
        this.member = member;
        this.setting = setting;
    }
}

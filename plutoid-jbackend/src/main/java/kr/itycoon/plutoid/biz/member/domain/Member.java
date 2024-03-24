package kr.itycoon.plutoid.biz.member.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.itycoon.plutoid.biz.common.model.BaseDto;
import kr.itycoon.plutoid.biz.common.model.MemberRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseDto {
    @JsonIgnore
    private String memberId;

    private String memberName;

    private String email;

    private String password;

    private MemberRoleEnum memberRole;

    private Date joinDate;

    private String photoUrl;

    @JsonIgnore
    private String activeYn;

    @JsonIgnore
    private String deleteYn;

    @JsonIgnore
    private Date deleteDate;

    @Builder
    public Member(String memberId, String memberName, String email, String password, MemberRoleEnum memberRole,
                  Date joinDate, String photoUrl, String activeYn, String deleteYn, Date deleteDate) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.memberRole = memberRole;
        this.joinDate = joinDate;
        this.photoUrl = photoUrl;
        this.activeYn = activeYn;
        this.deleteYn = deleteYn;
        this.deleteDate = deleteDate;
    }

}

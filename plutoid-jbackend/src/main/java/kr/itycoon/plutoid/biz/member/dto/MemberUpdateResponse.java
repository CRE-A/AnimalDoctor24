package kr.itycoon.plutoid.biz.member.dto;

import kr.itycoon.plutoid.biz.common.model.MemberRoleEnum;
import lombok.*;
import org.apache.naming.factory.SendMailFactory;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberUpdateResponse {
    @NonNull
    private String memberName;

    @NonNull
    private String email;

    @NonNull
    private String memberId;

    @NonNull
    private MemberRoleEnum memberRole;

    @NonNull
    private Date joinDate;

    @NonNull
    private String photoUrl;

    @Builder
    public MemberUpdateResponse(@NonNull String memberName, @NonNull String email, @NonNull String memberId, @NonNull MemberRoleEnum memberRole, @NonNull Date joinDate, @NonNull String photoUrl) {
        this.memberName = memberName;
        this.email = email;
        this.memberId = memberId;
        this.memberRole = memberRole;
        this.joinDate = joinDate;
        this.photoUrl = photoUrl;
    }
}

package kr.itycoon.plutoid.biz.member.dto;

import kr.itycoon.plutoid.biz.common.model.IssueForEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class VerifyCodeForMemberResponse {
    @NonNull
    private String memberId;

    @NonNull
    private String checkCode;

    @NonNull
    private IssueForEnum issueFor;
    
    @Builder
    public VerifyCodeForMemberResponse(@NonNull String memberId, @NonNull String checkCode, @NonNull IssueForEnum issueFor) {
        this.memberId = memberId;
        this.checkCode = checkCode;
        this.issueFor = issueFor;
    }
}

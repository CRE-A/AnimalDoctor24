package kr.itycoon.plutoid.biz.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.itycoon.plutoid.biz.common.model.IssueForEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class EmailCheckVerifyRequest {

    @NotNull
    @Email
    @Size(max = 100)
    private String email;

    @NotNull
    private String checkCode;

    @NotNull
    private IssueForEnum issueFor;

    @Builder
    public EmailCheckVerifyRequest(String email, String checkCode, IssueForEnum issueFor) {
        this.email = email;
        this.checkCode = checkCode;
        this.issueFor = issueFor;
    }

}

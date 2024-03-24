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
public class EmailCheckRegisterRequest {

    @NotNull
    @Email
    @Size(max = 100)
    private String email;

    @NotNull
    private IssueForEnum issueFor;

    @Builder
    public EmailCheckRegisterRequest(String email, IssueForEnum issueFor) {
        this.email = email;
        this.issueFor = issueFor;
    }

}






package kr.itycoon.plutoid.biz.member.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.itycoon.plutoid.biz.common.model.BaseDto;
import kr.itycoon.plutoid.biz.common.model.IssueForEnum;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class EmailCheck extends BaseDto {

    @NotNull
    @Email
    @Size(max = 100)
    private String email;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(max = 6)
    private String checkCode;

    @NotNull
    private IssueForEnum issueFor;

    @NotNull
    @JsonIgnore
    private Date issueDate;

    @NotNull
    @JsonIgnore
    private Date expireDate;

    @Builder
    public EmailCheck(String email, String checkCode, IssueForEnum issueFor, Date issueDate, Date expireDate) {
        this.email = email;
        this.checkCode = checkCode;
        this.issueFor = issueFor;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
    }
}

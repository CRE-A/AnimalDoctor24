package kr.itycoon.plutoid.biz.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum IssueForEnum  {
    JOIN, CHANGE, FORGOT;
    
    @JsonCreator
    public static IssueForEnum from(String issueFor) {
        return IssueForEnum.valueOf(issueFor.toUpperCase());
    }
   
}

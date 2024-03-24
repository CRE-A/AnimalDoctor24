package kr.itycoon.plutoid.biz.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MemberRoleEnum {
       ADMIN, MEMBER;
       
       @JsonCreator
       public static MemberRoleEnum from(String memberRole) {
           return MemberRoleEnum.valueOf(memberRole.toUpperCase());
       }
}

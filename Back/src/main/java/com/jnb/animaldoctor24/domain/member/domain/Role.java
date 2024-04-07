package com.jnb.animaldoctor24.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum Role {
    USER,ADMIN;

    @JsonCreator
    public static Role from(String role){return Role.valueOf(role.toUpperCase());}


}

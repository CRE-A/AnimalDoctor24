package com.pib102.pibApp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@NoArgsConstructor
@ToString
public class UserDto {
    private String id;
    private String pwd;
    private String name;
    private String email;
    private String dpt;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;
    private String authority;
    private Integer enabled;

    @Builder
    public UserDto(String id, String pwd, String name, String email, String dpt, String phone, Date regdate, Date expirationDate, String authority, Integer enabled) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.dpt = dpt;
        this.phone = phone;
        this.regdate = regdate;
        this.expirationDate = expirationDate;
        this.authority = authority;
        this.enabled = enabled;
    }
}

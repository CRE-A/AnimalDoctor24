package com.pib102.pibApp.mapper;

import com.pib102.pibApp.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 회원 생성 (회원가입)
     */
    int insertUser(UserDto user);

    /**
     * 회원 조회 (memberId)
     */
    UserDto findUserById(String id);
}

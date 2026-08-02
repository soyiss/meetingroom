package com.project.meetingroom.mapper;

import com.project.meetingroom.domain.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {

    // 로그인시 아이디로 회원정보(암호화된 비밀번호 포함)조회
    Users findByUsername(String username);

}

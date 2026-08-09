package com.project.meetingroom.service;

import com.project.meetingroom.domain.Users;
import com.project.meetingroom.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;


    /*
        로그인 기능

        아이디로 회원을 조회한 뒤
        사용자가 입력한 비밀번호와
        DB에 저장된 BCrypt 암호화 비밀번호를 비교한다.

        비밀번호가 일치하면 회원정보를 반환하고,
        일치하지 않으면 null을 반환한다.
    */
    public Users loginSuccess(Users users) {

        // 클라이언트가 작성한 아이디로 유저가 존재하는지 조회
        Users dbMember =
                usersMapper.findByUsername(users.getUsername());


        // 아이디가 존재하지 않는 경우
        if (dbMember == null) {
            return null;
        }


        // 사용자가 입력한 비밀번호와
        // DB에 저장된 BCrypt 비밀번호 비교
        if (passwordEncoder.matches(
                users.getPassword(),
                dbMember.getPassword())) {

            // 비밀번호가 일치하면 회원정보 반환
            return dbMember;
        }


        // 비밀번호가 일치하지 않는 경우
        return null;
    }

}
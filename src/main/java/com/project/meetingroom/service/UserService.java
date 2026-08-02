package com.project.meetingroom.service;

import com.project.meetingroom.domain.Users;
import com.project.meetingroom.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersMapper usersMapper;

    /* 로그인 기능
        아이디로 회원을 조회한 뒤 사용자가 입력한 비밀번호와 db에 저장된 비밀번호를 비교
        일치하면 회원정보를 반환하고, 일치하지 않으면 null을 반환한다.
    */
    public Users loginSuccess(Users users){

        //클라이언트가 작성한 id로 유저가 존재하는지 sql에서 조회
        Users db멤버 = usersMapper.findByUsername(users.getUsername());

        // 아이디가 존재하지 않는 경우
        // if else for문 구문에서 {} 내부에 존재하는 코드가 한줄 일 경우 {}를 생략가능
        if (db멤버 == null) return  null;


        // 클라이언트가 작성한 비밀번호를 db에 저장된 비밀번호와 일치하는지 확인

        if (users.getPassword().equals(db멤버.getPassword())) {
            boolean 비밀번호일치 =
                    users.getPassword().equals(db멤버.getPassword());
        }else{
            return null; // 비밀번호가 다르다면

        }
        // 아이디에 존재하는 비밀번호가 맞다면
        return db멤버;

    }


}

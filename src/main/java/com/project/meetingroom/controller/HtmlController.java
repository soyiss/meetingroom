package com.project.meetingroom.controller;


import com.project.meetingroom.domain.Users;
import com.project.meetingroom.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HtmlController {

    private final UserService userService;

    @GetMapping("/")
    public String indexHtml() {
        return "index";

    }

    @GetMapping("/login")
    public String loginHtml() {
        return "login";

    }
    /* 로그인 처리
     * 성공하면 세션에 로그인 정보를 저장하고 메인페이지로 이동
     * 실패하면 로그인 페이지로 다시 이동하면서 에러 메세지를 전달
     */

    @PostMapping("/login")
    public String login(Users users, HttpSession session, Model model) {
        Users loginMember = userService.loginSuccess(users);

        if (loginMember == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");

            return "login";
        }
        // 아이디 비밀번호가 존재한다면 세션에 로그인한 회원 정보를 저장한다.
        // 비밀번호는 굳이 저장하지 않아도 된다.

        session.setAttribute("loginMember", loginMember);

        // 로그인 세션(쿠키)의 유효시간을 30분으로 지정
        // 브라우저에 발급되는 JSESSIONID 쿠키가 30분간 활동이 없으면 자동 만료처리(자동 로그아웃)
        session.setMaxInactiveInterval(60 * 30); // 60초를 30번 -> 30분

        return "redirect:/";
    }


    /*로그아웃 처리
     * 세션을 초기화하고 메인페이지로이동한다.
     */

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate(); // 세션에 있는 로그인 정보 지우기
        return "redirect:/";    // index.html로 이동

    }











}

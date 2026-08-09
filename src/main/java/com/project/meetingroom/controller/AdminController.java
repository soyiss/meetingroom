package com.project.meetingroom.controller;

import com.project.meetingroom.dto.LoginUser;
import com.project.meetingroom.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ReservationService reservationService;


    // 관리자 페이지
    @GetMapping("/admin")
    public String admin(
            HttpSession session,
            Model model) {

        LoginUser loginMember =
                (LoginUser) session.getAttribute("loginMember");


        // 로그인하지 않은 경우
        if (loginMember == null) {
            return "redirect:/login";
        }


        // admin 계정이 아닌 경우
        if (!"admin".equals(loginMember.getUsername())) {
            return "redirect:/";
        }


        // 전체 예약 조회
        model.addAttribute(
                "reservations",
                reservationService.findAllReservationsForAdmin()
        );

        return "admin";
    }

}
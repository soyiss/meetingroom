package com.project.meetingroom.controller;

import com.project.meetingroom.domain.Reservation;
import com.project.meetingroom.domain.Rooms;
import com.project.meetingroom.domain.Users;
import com.project.meetingroom.service.ReservationService;
import com.project.meetingroom.service.RoomsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RoomsController {


    private final RoomsService roomsService;
    private final ReservationService reservationService;


    @GetMapping("/rooms/{id}")
    public String roomDetail(@PathVariable Long id,
                             Model model) {

        Rooms room = roomsService.findByRooms(id);
        model.addAttribute("room", room);

        return "rooms";
    }

    @PostMapping("/reservation")
    public String reservation(Reservation reservation, HttpSession session, Model model) {

        // 로그인한 사용자 가져오기
        Users loginMember = (Users) session.getAttribute("loginMember");

        // 로그인 안 되어 있으면 로그인 페이지
        if (loginMember == null) {
            return "redirect:/login";
        }

        // 예약하는 사람 번호 저장
        reservation.setUserId(loginMember.getId());

        try {

            reservationService.reserve(reservation);

        } catch (IllegalStateException e) {

            model.addAttribute("room",
                    roomsService.findByRooms(reservation.getRoomId()));

            model.addAttribute("error", e.getMessage());

            return "rooms";
        }

        return "redirect:/";
    }


}

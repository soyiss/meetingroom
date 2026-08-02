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

        model.addAttribute(
                "reservations",
                reservationService.findReservationsByRoomId(id)
        );
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

    @GetMapping("/myreservations")
    public String myReservations(HttpSession session,
                                 Model model) {

        Users loginMember =
                (Users) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "reservations",
                reservationService.myReserve(loginMember.getId())
        );

        return "myreservations";
    }

    // 수정화면으로 이동
    @GetMapping("/reservation/update/{id}")
    public String updateReservation(@PathVariable Long id,
                                    Model model) {

        Reservation reservation = reservationService.findById(id);

        model.addAttribute("reservation", reservation);

        model.addAttribute(
                "reservations",
                reservationService.findReservationsByRoomId(reservation.getRoomId())
        );

        return "updateReservation";
    }



    // 수정완료
    @PostMapping("/reservation/update")
    public String updateReservation(Reservation reservation,
                                    Model model) {

        try {

            reservationService.updateReservation(reservation);

        } catch (IllegalStateException e) {

            model.addAttribute("reservation", reservation);
            model.addAttribute("error", e.getMessage());

            return "updateReservation";
        }

        return "redirect:/myreservations";
    }

    // 삭제기능
    @GetMapping("/reservation/delete/{id}")
    public String deleteReservation(@PathVariable Long id) {

        reservationService.deleteReservation(id);

        return "redirect:/myreservations";
    }

}

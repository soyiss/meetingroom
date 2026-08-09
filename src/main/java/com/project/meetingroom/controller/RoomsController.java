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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String reservation(Reservation reservation, HttpSession session, Model model, RedirectAttributes redirectAttributes) {

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

            // 회의실 정보 다시 조회
            model.addAttribute(
                    "room",
                    roomsService.findByRooms(reservation.getRoomId())
            );

            // 에러 메시지 전달
            model.addAttribute("error", e.getMessage());

            // 예약 현황도 다시 조회
            model.addAttribute(
                    "reservations",
                    reservationService.findReservationsByRoomId(
                            reservation.getRoomId()
                    )
            );

            return "rooms";
        }

        // 예약 성공 메시지 전달
        redirectAttributes.addFlashAttribute(
                "message",
                "예약이 완료되었습니다."
        );

        return  "redirect:/myreservations";
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

    // 전체 예약현황 화면
    @GetMapping("/reservations")
    public String allReservations(HttpSession session,
                                  Model model) {

        // 로그인 안 되어 있으면 로그인 페이지
        if (session.getAttribute("loginMember") == null) {
            return "redirect:/login";
        }

        // 전체 회의실의 예약 현황 조회
        model.addAttribute(
                "reservations",
                reservationService.findAllReservations()
        );

        return "calendar";
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
                                    Model model, RedirectAttributes redirectAttributes) {

        try {

            reservationService.updateReservation(reservation);

        } catch (IllegalStateException e) {

            model.addAttribute("reservation", reservation);
            model.addAttribute("error", e.getMessage());

            return "updateReservation";
        }

        // 예약 수정 메시지 전달
        redirectAttributes.addFlashAttribute(
                "message",
                "수정이 완료되었습니다."
        );
        return "redirect:/myreservations";
    }

    // 삭제기능
    @PostMapping("/reservation/delete/{id}")
    public String deleteReservation(@PathVariable Long id,
                                    RedirectAttributes redirectAttributes) {

        reservationService.deleteReservation(id);

        // 예약 삭제 메시지 전달
        redirectAttributes.addFlashAttribute(
                "message",
                "삭제가 완료되었습니다."
        );

        return "redirect:/myreservations";
    }

}

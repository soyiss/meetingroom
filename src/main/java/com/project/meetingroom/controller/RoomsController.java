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
                                    HttpSession session,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {

        Users loginMember =
                (Users) session.getAttribute("loginMember");

        // 로그인하지 않은 경우
        if (loginMember == null) {
            return "redirect:/login";
        }

        Reservation reservation =
                reservationService.findById(id);

        // 예약이 존재하지 않는 경우
        if (reservation == null) {
            redirectAttributes.addFlashAttribute(
                    "message",
                    "존재하지 않는 예약입니다."
            );

            return "redirect:/myreservations";
        }

        // 관리자 또는 예약자 본인만 수정 가능
        if (!"admin".equals(loginMember.getUsername())
                && !loginMember.getId().equals(reservation.getUserId())) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "본인의 예약만 수정할 수 있습니다."
            );

            return "redirect:/myreservations";
        }

        model.addAttribute("reservation", reservation);

        model.addAttribute(
                "reservations",
                reservationService.findReservationsByRoomId(
                        reservation.getRoomId()
                )
        );

        return "updateReservation";
    }



    // 수정완료
    @PostMapping("/reservation/update")
    public String updateReservation(
            Reservation reservation,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        Users loginMember =
                (Users) session.getAttribute("loginMember");

        // 로그인 확인
        if (loginMember == null) {
            return "redirect:/login";
        }

        // 기존 예약 조회
        Reservation existingReservation =
                reservationService.findById(reservation.getId());

        // 예약 존재 여부 확인
        if (existingReservation == null) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "존재하지 않는 예약입니다."
            );

            return "redirect:/myreservations";
        }

        // 관리자 또는 예약자 본인만 수정 가능
        if (!"admin".equals(loginMember.getUsername())
                && !loginMember.getId().equals(existingReservation.getUserId())) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "본인의 예약만 수정할 수 있습니다."
            );

            return "redirect:/myreservations";
        }

        // 기존 예약의 userId를 사용
        reservation.setUserId(existingReservation.getUserId());

        try {

            reservationService.updateReservation(reservation);

        } catch (IllegalStateException e) {

            model.addAttribute("reservation", reservation);
            model.addAttribute("error", e.getMessage());

            return "updateReservation";
        }

        redirectAttributes.addFlashAttribute(
                "message",
                "수정이 완료되었습니다."
        );

        return "redirect:/myreservations";
    }

    // 예약 취소
    @PostMapping("/reservation/cancel/{id}")
    public String cancelReservation(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Users loginMember =
                (Users) session.getAttribute("loginMember");

        // 로그인 확인
        if (loginMember == null) {
            return "redirect:/login";
        }

        Reservation reservation =
                reservationService.findById(id);

        // 예약 존재 여부
        if (reservation == null) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "존재하지 않는 예약입니다."
            );

            return "redirect:/myreservations";
        }

        // 관리자 또는 예약자 본인만 취소 가능
        if (!"admin".equals(loginMember.getUsername())
                && !loginMember.getId().equals(reservation.getUserId())) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "본인의 예약만 취소할 수 있습니다."
            );

            return "redirect:/myreservations";
        }

        reservationService.cancelReservation(id);

        redirectAttributes.addFlashAttribute(
                "message",
                "예약이 취소되었습니다."
        );

        // 관리자가 취소했다면 관리자 페이지로
        if ("admin".equals(loginMember.getUsername())) {
            return "redirect:/admin";
        }

        return "redirect:/myreservations";
    }

}

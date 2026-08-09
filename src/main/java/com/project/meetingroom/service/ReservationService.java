package com.project.meetingroom.service;

import com.project.meetingroom.domain.Reservation;
import com.project.meetingroom.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;


    // 예약
    public void reserve(Reservation reservation) {

        // 시작시간과 종료시간 유효성 검사
        if (!reservation.getStartTime().isBefore(reservation.getEndTime())) {
            throw new IllegalStateException(
                    "종료시간은 시작시간보다 늦어야 합니다."
            );
        }


        // 예약 시간 중복 검사
        int overlap =
                reservationMapper.countOverlap(reservation);

        if (overlap > 0) {
            throw new IllegalStateException(
                    "이미 예약되어 있습니다."
            );
        }


        reservationMapper.insertReservation(reservation);
    }


    // 특정 회의실 예약 현황 조회
    public List<Reservation> findReservationsByRoomId(Long id) {
        return reservationMapper.findReservationsByRoomId(id);
    }


    // 전체 회의실의 예약 현황 조회
    public List<Reservation> findAllReservations() {
        return reservationMapper.findAllReservations();
    }


    // 내 예약 조회
    public List<Reservation> myReserve(Long userId) {
        return reservationMapper.findByUserId(userId);
    }


    // 예약 조회
    public Reservation findById(Long id) {
        return reservationMapper.findById(id);
    }


    // 예약 수정
    public void updateReservation(Reservation reservation) {

        // 시작시간과 종료시간 유효성 검사
        if (!reservation.getStartTime().isBefore(reservation.getEndTime())) {
            throw new IllegalStateException(
                    "종료시간은 시작시간보다 늦어야 합니다."
            );
        }


        // 예약 시간 중복 검사
        int overlap =
                reservationMapper.countOverlapForUpdate(reservation);

        if (overlap > 0) {
            throw new IllegalStateException(
                    "이미 예약된 시간입니다."
            );
        }


        reservationMapper.updateReservation(reservation);
    }


    // 예약 취소
    public void cancelReservation(Long id) {
        reservationMapper.cancelReservation(id);
    }


    // 관리자 전체 예약 조회
    public List<Reservation> findAllReservationsForAdmin() {
        return reservationMapper.findAllReservationsForAdmin();
    }

}
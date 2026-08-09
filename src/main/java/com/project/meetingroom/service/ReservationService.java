package com.project.meetingroom.service;

import com.project.meetingroom.domain.Reservation;
import com.project.meetingroom.domain.Users;
import com.project.meetingroom.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationMapper reservationMapper;

    public void reserve(Reservation reservation){

        int overlap = reservationMapper.countOverlap(reservation);

        if(overlap > 0){
            throw new IllegalStateException("이미 예약되어 있습니다.");
        }

        reservationMapper.insertReservation(reservation);
    }

    public List<Reservation> findReservationsByRoomId(Long id) {
        return reservationMapper.findReservationsByRoomId(id);
    }


    // 전체 회의실의 예약 현황 조회
    public List<Reservation> findAllReservations() {
        return reservationMapper.findAllReservations();
    }

    public List<Reservation> myReserve(Long userId) {
        return reservationMapper.findByUserId(userId);
    }

    public Reservation findById(Long id) {
        return reservationMapper.findById(id);
    }

    public void updateReservation(Reservation reservation) {

        int overlap = reservationMapper.countOverlapForUpdate(reservation);

        if(overlap > 0){
            throw new IllegalStateException("이미 예약된 시간입니다.");
        }


        reservationMapper.updateReservation(reservation);
    }

    public void deleteReservation(Long id) {
        reservationMapper.deleteReservation(id);
    }


}

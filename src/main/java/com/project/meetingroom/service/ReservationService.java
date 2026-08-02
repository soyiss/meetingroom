package com.project.meetingroom.service;

import com.project.meetingroom.domain.Reservation;
import com.project.meetingroom.domain.Users;
import com.project.meetingroom.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}

package com.project.meetingroom.mapper;

import com.project.meetingroom.domain.Reservation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper {

    int countOverlap(Reservation reservation);

    void insertReservation(Reservation reservation);


}

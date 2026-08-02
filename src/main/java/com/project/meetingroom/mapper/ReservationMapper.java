package com.project.meetingroom.mapper;

import com.project.meetingroom.domain.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {

    int countOverlap(Reservation reservation);

    void insertReservation(Reservation reservation);

    List<Reservation> findReservationsByRoomId(Long id);

    List<Reservation> findByUserId(@Param("userId") Long userId);

    Reservation findById(Long id);

    int countOverlapForUpdate(Reservation reservation);

    void updateReservation(Reservation reservation);


}

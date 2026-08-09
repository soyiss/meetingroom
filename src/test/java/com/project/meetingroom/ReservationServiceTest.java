package com.project.meetingroom.service;

import com.project.meetingroom.domain.Reservation;
import com.project.meetingroom.mapper.ReservationMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationService reservationService;


    // 1. 정상적인 예약이 성공하는지 테스트
    @Test
    void 예약_성공() {

        Reservation reservation = new Reservation();

        reservation.setRoomId(1L);
        reservation.setUserId(1L);
        reservation.setReserveDate(
                LocalDate.of(2026, 8, 20)
        );
        reservation.setStartTime(
                LocalTime.of(10, 0)
        );
        reservation.setEndTime(
                LocalTime.of(12, 0)
        );

        // 기존 예약이 없다고 가정
        when(reservationMapper.countOverlap(reservation))
                .thenReturn(0);

        // 예약 실행
        reservationService.reserve(reservation);

        // 실제로 예약 저장 메서드가 호출되었는지 확인
        verify(reservationMapper)
                .insertReservation(reservation);
    }


    // 2. 종료시간이 시작시간보다 빠르면 예약 실패
    @Test
    void 종료시간이_시작시간보다_빠르면_예약실패() {

        Reservation reservation = new Reservation();

        reservation.setRoomId(1L);
        reservation.setUserId(1L);
        reservation.setReserveDate(
                LocalDate.of(2026, 8, 20)
        );
        reservation.setStartTime(
                LocalTime.of(14, 0)
        );
        reservation.setEndTime(
                LocalTime.of(13, 0)
        );

        // 예외가 발생하는지 확인
        assertThrows(
                IllegalStateException.class,
                () -> reservationService.reserve(reservation)
        );

        // 잘못된 예약은 DB에 저장되면 안 됨
        verify(reservationMapper, never())
                .insertReservation(any(Reservation.class));
    }


    // 3. 시작시간과 종료시간이 같으면 예약 실패
    @Test
    void 시작시간과_종료시간이_같으면_예약실패() {

        Reservation reservation = new Reservation();

        reservation.setRoomId(1L);
        reservation.setUserId(1L);
        reservation.setReserveDate(
                LocalDate.of(2026, 8, 20)
        );
        reservation.setStartTime(
                LocalTime.of(10, 0)
        );
        reservation.setEndTime(
                LocalTime.of(10, 0)
        );

        // 예외가 발생하는지 확인
        assertThrows(
                IllegalStateException.class,
                () -> reservationService.reserve(reservation)
        );

        // 잘못된 예약은 DB에 저장되면 안 됨
        verify(reservationMapper, never())
                .insertReservation(any(Reservation.class));
    }


    // 4. 기존 예약과 시간이 겹치면 예약 실패
    @Test
    void 예약시간이_중복되면_예약실패() {

        Reservation reservation = new Reservation();

        reservation.setRoomId(1L);
        reservation.setUserId(1L);
        reservation.setReserveDate(
                LocalDate.of(2026, 8, 20)
        );
        reservation.setStartTime(
                LocalTime.of(11, 0)
        );
        reservation.setEndTime(
                LocalTime.of(13, 0)
        );

        // 기존 예약이 있다고 가정
        when(reservationMapper.countOverlap(reservation))
                .thenReturn(1);

        // 예외가 발생하는지 확인
        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> reservationService.reserve(reservation)
                );

        // 에러 메시지도 확인
        assertEquals(
                "이미 예약되어 있습니다.",
                exception.getMessage()
        );

        // 중복 예약은 DB에 저장되면 안 됨
        verify(reservationMapper, never())
                .insertReservation(any(Reservation.class));
    }
}
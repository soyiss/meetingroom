package com.project.meetingroom.mapper;

import com.project.meetingroom.domain.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {

    // 예약하려는 시간에 이미 예약이 존재하는지 확인
    int countOverlap(Reservation reservation);

    // 새로운 예약 등록
    void insertReservation(Reservation reservation);

    // 선택한 회의실의 예약 현황 전체 조회
    List<Reservation> findReservationsByRoomId(Long id);

    // 로그인한 사용자의 예약 목록 조회 (내 예약현황)
    List<Reservation> findByUserId(@Param("userId") Long userId);

    // 예약번호(id)로 예약 1건 조회
    // 예약 수정 화면에서 기존 예약 정보를 불러올 때 사용
    Reservation findById(Long id);

    // 예약 수정 시 다른 예약과 시간이 겹치는지 확인
    // 현재 수정 중인 예약(id)은 제외하고 검사
    int countOverlapForUpdate(Reservation reservation);

    // 예약 정보 수정
    // 예약 날짜, 시작시간, 종료시간 변경
    void updateReservation(Reservation reservation);

    // 예약 삭제
    void deleteReservation(Long id);
}

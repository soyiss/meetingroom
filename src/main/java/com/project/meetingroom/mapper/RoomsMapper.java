package com.project.meetingroom.mapper;

import com.project.meetingroom.domain.Users;
import org.apache.ibatis.annotations.Mapper;
import com.project.meetingroom.domain.Rooms;

import java.util.List;


@Mapper
public interface RoomsMapper {

    // 회의실 전체 목록 조회
    List<Rooms> findAllRooms();

    // 회의실 1개 조회
    Rooms findByRooms(Long id);


}

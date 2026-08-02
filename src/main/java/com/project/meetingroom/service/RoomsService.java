package com.project.meetingroom.service;


import com.project.meetingroom.domain.Rooms;
import com.project.meetingroom.mapper.RoomsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomsService {
    private final RoomsMapper roomsMapper;

    public List<Rooms> findAllRooms() {
        return roomsMapper.findAllRooms();
    }

    public Rooms findByRooms(Long id) {
        return roomsMapper.findByRooms(id);
    }

}

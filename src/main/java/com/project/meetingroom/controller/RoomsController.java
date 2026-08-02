package com.project.meetingroom.controller;

import com.project.meetingroom.domain.Rooms;
import com.project.meetingroom.service.RoomsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class RoomsController {


    private final RoomsService roomsService;

    @GetMapping("/rooms/{id}")
    public String roomDetail(@PathVariable Long id,
                             Model model) {

        Rooms room = roomsService.findByRooms(id);
        model.addAttribute("room", room);

        return "rooms";
    }


}

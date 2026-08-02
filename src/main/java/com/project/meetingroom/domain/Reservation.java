package com.project.meetingroom.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Reservation {

    private Long id;
    private Long roomId;
    private Long userId;
    private LocalDate reserveDate;
    private LocalTime startTime;
    private LocalTime  endTime;
    private String status;


}

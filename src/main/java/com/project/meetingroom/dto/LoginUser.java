package com.project.meetingroom.dto;

import com.project.meetingroom.domain.Users;
import lombok.Getter;

@Getter
public class LoginUser {

    private final Long id;
    private final String username;

    public LoginUser(Users users) {
        this.id = users.getId();
        this.username = users.getUsername();
    }
}
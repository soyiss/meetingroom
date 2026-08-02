package com.project.meetingroom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.project.meetingroom.mapper")
@SpringBootApplication
public class MeetingroomApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingroomApplication.class, args);
	}

}

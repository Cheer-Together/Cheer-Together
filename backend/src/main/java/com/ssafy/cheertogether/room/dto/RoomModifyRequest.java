package com.ssafy.cheertogether.room.dto;

import com.ssafy.cheertogether.room.domain.RoomStatus;

import lombok.Getter;

@Getter
public class RoomModifyRequest {
	Long id;
	Long gameId;
	String name;
	RoomStatus status;
	String password;
}

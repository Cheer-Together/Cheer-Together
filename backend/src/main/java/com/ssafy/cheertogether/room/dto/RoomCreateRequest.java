package com.ssafy.cheertogether.room.dto;

import com.ssafy.cheertogether.room.domain.RoomStatus;

import lombok.Getter;

@Getter
public class RoomCreateRequest {
	private Long gameId;
	private String name;
	private RoomStatus roomStatus;
	private String password;
	private String managerId;
	private String sessionId;
}

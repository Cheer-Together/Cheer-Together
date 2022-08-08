package com.ssafy.cheertogether.room.dto;

import com.ssafy.cheertogether.room.domain.Room;
import com.ssafy.cheertogether.room.domain.RoomStatus;

import lombok.Getter;

@Getter
public class RoomResponse {
	private final long roomId;
	private final long gameId;
	private final String name;
	private final RoomStatus status;
	private final String password;
	private final String managerId;

	public RoomResponse(Room room) {
		roomId = room.getId();
		gameId = room.getGame().getId();
		name = room.getName();
		status = room.getStatus();
		password = room.getPassword();
		managerId = room.getManagerId();
	}
}

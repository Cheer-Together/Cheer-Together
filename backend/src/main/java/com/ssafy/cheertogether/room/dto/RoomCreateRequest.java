package com.ssafy.cheertogether.room.dto;

import com.ssafy.cheertogether.room.domain.RoomStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class RoomCreateRequest {
	private Long matchId;
	private String name;
	private RoomStatus roomStatus;
	private String password;
	private String managerId;
}

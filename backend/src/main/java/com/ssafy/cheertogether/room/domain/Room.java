package com.ssafy.cheertogether.room.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ssafy.cheertogether.room.dto.RoomCreateRequest;
import com.ssafy.cheertogether.room.dto.RoomModifyRequest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long matchId;

	private String name;

	@Enumerated(EnumType.STRING)
	private RoomStatus status;
	private String password;

	private String managerId;

	@Builder
	public Room(Long id, Long matchId, String name, RoomStatus status, String password, String managerId) {
		this.id = id;
		this.matchId = matchId;
		this.name = name;
		this.status = status;
		this.password = password;
		this.managerId = managerId;
	}

	public static Room from(RoomCreateRequest roomCreateRequest) {
		return Room.builder()
			.matchId(roomCreateRequest.getMatchId())
			.name(roomCreateRequest.getName())
			.status(roomCreateRequest.getRoomStatus())
			.password(roomCreateRequest.getPassword())
			.managerId(roomCreateRequest.getManagerId())
			.build();
	}

	public void update(RoomModifyRequest roomModifyRequest) {
		matchId = roomModifyRequest.getMatchId();
		name = roomModifyRequest.getName();
		status = roomModifyRequest.getStatus();
		password = roomModifyRequest.getPassword();
	}
}

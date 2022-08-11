package com.ssafy.cheertogether.room.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.cheertogether.game.domain.Game;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private Game game;

	private String name;

	@Enumerated(EnumType.STRING)
	private RoomStatus status;
	private String password;

	private String managerId;
	private String sessionId;

	@Builder
	public Room(Long id, Game game, String name, RoomStatus status, String password, String managerId, String sessionId) {
		this.id = id;
		this.game = game;
		this.name = name;
		this.status = status;
		this.password = password;
		this.managerId = managerId;
		this.sessionId = sessionId;
	}

	public static Room from(RoomCreateRequest roomCreateRequest, Game game) {
		return Room.builder()
			.game(game)
			.name(roomCreateRequest.getName())
			.status(roomCreateRequest.getRoomStatus())
			.password(roomCreateRequest.getPassword())
			.managerId(roomCreateRequest.getManagerId())
			.sessionId(roomCreateRequest.getSessionId())
			.build();
	}

	public void update(RoomModifyRequest roomModifyRequest, Game game) {
		this.game = game;
		name = roomModifyRequest.getName();
		status = roomModifyRequest.getStatus();
		password = roomModifyRequest.getPassword();
	}
}

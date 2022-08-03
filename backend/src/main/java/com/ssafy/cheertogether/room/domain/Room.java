package com.ssafy.cheertogether.room.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class Room {
	@Id
	@GeneratedValue
	private Long id;

	private Long matchId;

	private String name;

	@Enumerated(EnumType.STRING)
	private RoomStatus status;
	private String password;

	private String managerId;
}

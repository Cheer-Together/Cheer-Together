package com.ssafy.cheertogether.game.dto;

import java.time.LocalDateTime;

import com.ssafy.cheertogether.game.domain.GameStatus;

import lombok.Getter;

@Getter
public class GameModifyRequest {
	private LocalDateTime kickoff;
	private String stadium;
	private GameStatus status;
	private Integer homeScore;
	private Integer awayScore;
	private Long apiId;
}

package com.ssafy.cheertogether.game.dto;

import java.time.LocalDateTime;

import com.ssafy.cheertogether.game.domain.GameStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameRegisterRequest {
	private LocalDateTime kickoff;
	private String stadium;
	private GameStatus status;
	private Integer homeScore;
	private Integer awayScore;
	private Long apiId;
	private Long leagueApiId;
}

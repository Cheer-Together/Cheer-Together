package com.ssafy.cheertogether.game.dto;

import java.time.LocalDateTime;

import com.ssafy.cheertogether.game.domain.Game;
import com.ssafy.cheertogether.game.domain.GameStatus;
import com.ssafy.cheertogether.team.domain.Team;
import com.ssafy.cheertogether.team.dto.TeamResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameResponse {
	private Long id;
	private TeamResponse home;
	private TeamResponse away;
	private LocalDateTime kickoff;
	private String stadium;
	private GameStatus status;
	private Integer homeScore;
	private Integer awayScore;
	private Long apiId;

	public static GameResponse from(Game game) {
		return GameResponse.builder()
			.id(game.getId())
			.home(TeamResponse.from(game.getHome()))
			.away(TeamResponse.from(game.getAway()))
			.kickoff(game.getKickoff())
			.stadium(game.getStadium())
			.status(game.getStatus())
			.homeScore(game.getHomeScore())
			.awayScore(game.getAwayScore())
			.apiId(game.getApiId())
			.build();
	}
}

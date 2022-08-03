package com.ssafy.cheertogether.match.dto;

import java.time.LocalDateTime;

import com.ssafy.cheertogether.match.domain.Match;
import com.ssafy.cheertogether.match.domain.MatchStatus;
import com.ssafy.cheertogether.team.domain.Team;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MatchResponse {
	private Long id;
	private Team home;
	private Team away;
	private LocalDateTime kickoff;
	private String stadium;
	private MatchStatus status;
	private Integer homeScore;
	private Integer awayScore;
	private Long apiId;

	public MatchResponse(Match match) {
		id = match.getApiId();
		home = match.getHome();
		away = match.getAway();
		kickoff = match.getKickoff();
		stadium = match.getStadium();
		status = match.getStatus();
		homeScore = match.getHomeScore();
		awayScore = match.getAwayScore();
		apiId = match.getApiId();
	}
}

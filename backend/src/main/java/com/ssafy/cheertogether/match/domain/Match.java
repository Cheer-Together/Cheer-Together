package com.ssafy.cheertogether.match.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ssafy.cheertogether.match.dto.MatchModifyRequest;
import com.ssafy.cheertogether.team.domain.Team;

import lombok.Getter;

@Entity
@Getter
public class Match {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "home_team_id")
	private Team home;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "away_team_id")
	private Team away;

	private LocalDateTime kickoff;
	private String stadium;

	@Enumerated(EnumType.STRING)
	private MatchStatus status;

	private Integer homeScore;
	private Integer awayScore;
	private Long apiId;

	public void updateMatchInfos(MatchModifyRequest matchModifyRequest) {
		kickoff = matchModifyRequest.getKickoff();
		stadium = matchModifyRequest.getStadium();
		status = matchModifyRequest.getStatus();
		homeScore = matchModifyRequest.getHomeScore();
		awayScore = matchModifyRequest.getAwayScore();
		apiId = matchModifyRequest.getApiId();
	}
}

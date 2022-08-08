package com.ssafy.cheertogether.game.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.cheertogether.game.dto.GameModifyRequest;
import com.ssafy.cheertogether.room.domain.Room;
import com.ssafy.cheertogether.team.domain.Team;

import lombok.Getter;

@Entity
@Getter
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "home_team_id")
	private Team home;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "away_team_id")
	private Team away;

	private LocalDateTime kickoff;
	private String stadium;

	@Enumerated(EnumType.STRING)
	private GameStatus status;

	private Integer homeScore;
	private Integer awayScore;
	private Long apiId;
	private Long leagueId;

	@OneToMany(mappedBy = "game")
	@JsonManagedReference
	private List<Room> roomList = new ArrayList<>();

	public void updateGameInfos(GameModifyRequest gameModifyRequest) {
		kickoff = gameModifyRequest.getKickoff();
		stadium = gameModifyRequest.getStadium();
		status = gameModifyRequest.getStatus();
		homeScore = gameModifyRequest.getHomeScore();
		awayScore = gameModifyRequest.getAwayScore();
		apiId = gameModifyRequest.getApiId();
	}
}

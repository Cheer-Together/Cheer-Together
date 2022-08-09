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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.cheertogether.game.dto.GameModifyRequest;
import com.ssafy.cheertogether.game.dto.GameRegisterRequest;
import com.ssafy.cheertogether.room.domain.Room;
import com.ssafy.cheertogether.team.domain.Team;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "home_team_api_id", referencedColumnName = "api_id")
	private Team home;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "away_team_api_id", referencedColumnName = "api_id")
	private Team away;

	private LocalDateTime kickoff;
	private String stadium;

	@Enumerated(EnumType.STRING)
	private GameStatus status;

	@ColumnDefault("null")
	private Integer homeScore;
	@ColumnDefault("null")
	private Integer awayScore;
	private Long apiId;
	private Long leagueApiId;

	@OneToMany(mappedBy = "game")
	@JsonManagedReference
	private List<Room> roomList = new ArrayList<>();

	@Builder
	public Game(Team home, Team away, LocalDateTime kickoff, String stadium, GameStatus status, Integer homeScore, Integer awayScore, Long apiId, Long leagueApiId ){
		this.home = home;
		this.away = away;
		this.kickoff = kickoff;
		this.stadium = stadium;
		this.status = status;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		this.apiId = apiId;
		this.leagueApiId = leagueApiId;
	}

	public static Game from(GameRegisterRequest gameRegisterRequest, Team home, Team away){
		return Game.builder()
			.home(home)
			.away(away)
			.kickoff(gameRegisterRequest.getKickoff())
			.stadium(gameRegisterRequest.getStadium())
			.status(gameRegisterRequest.getStatus())
			.homeScore(gameRegisterRequest.getHomeScore())
			.awayScore(gameRegisterRequest.getAwayScore())
			.apiId(gameRegisterRequest.getApiId())
			.leagueApiId(gameRegisterRequest.getLeagueApiId())
			.build();
	}

	public void updateGameInfos(GameModifyRequest gameModifyRequest) {
		kickoff = gameModifyRequest.getKickoff();
		stadium = gameModifyRequest.getStadium();
		status = gameModifyRequest.getStatus();
		homeScore = gameModifyRequest.getHomeScore();
		awayScore = gameModifyRequest.getAwayScore();
		apiId = gameModifyRequest.getApiId();
	}
}

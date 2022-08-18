package com.ssafy.cheertogether.game.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.cheertogether.team.domain.Team;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long gameId;

	@Embedded
	private Time time;

	@ManyToOne
	@JoinColumn(name = "team_id", referencedColumnName = "api_id")
	private Team team;

	@Embedded
	private Player player;

	@Embedded
	private Assist assist;

	private String type;
	private String detail;
	private String comments;
}

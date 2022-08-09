package com.ssafy.cheertogether.league.dto;

import java.util.List;

import com.ssafy.cheertogether.league.domain.League;
import com.ssafy.cheertogether.team.domain.Team;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LeagueResponse {

	private String name;
	private String hanName;
	private String logo;
	private Long apiId;
	private List<Team> teamList;

	public LeagueResponse(League league) {
		this.name = league.getName();
		this.hanName = league.getHanName();
		this.logo = league.getLogo();
		this.apiId = league.getApiId();
		this.teamList = league.getTeamList();
	}
}

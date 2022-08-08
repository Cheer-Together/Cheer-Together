package com.ssafy.cheertogether.league.dto;

import com.ssafy.cheertogether.league.domain.League;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LeagueResponseExceptTeamList {
	private String name;
	private String hanName;
	private String logo;
	private Long apiId;

	public LeagueResponseExceptTeamList(League league) {
		this.name = league.getName();
		this.hanName = league.getHanName();
		this.logo = league.getLogo();
		this.apiId = league.getApiId();
	}
}

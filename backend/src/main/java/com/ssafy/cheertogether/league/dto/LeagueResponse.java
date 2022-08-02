package com.ssafy.cheertogether.league.dto;

import java.util.List;

import com.ssafy.cheertogether.league.domain.League;
import com.ssafy.cheertogether.team.domain.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "leagueBuilder")
public class LeagueResponse {

	private String name;
	private String logo;
	private int apiId;
	private List<Team> teamList;

	public static LeagueResponse from(final League league) {
		return LeagueResponse.leagueBuilder()
			.name(league.getName())
			.logo(league.getLogo())
			.apiId(league.getApiId())
			.teamList(league.getTeamList())
			.build();
	}
}

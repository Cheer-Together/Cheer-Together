package com.ssafy.cheertogether.league.dto;

import com.ssafy.cheertogether.league.domain.League;

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

	public static LeagueResponse from(final League league) {
		return LeagueResponse.leagueBuilder()
			.name(league.getName())
			.logo(league.getLogo())
			.apiId(league.getApiId())
			.build();
	}
}

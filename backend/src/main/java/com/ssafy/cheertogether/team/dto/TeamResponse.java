package com.ssafy.cheertogether.team.dto;

import com.ssafy.cheertogether.team.domain.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "teamResponseBuilder")
public class TeamResponse {

	private String name;
	private String logo;
	private String code;
	private int apiId;

	public static TeamResponse from(final Team team) {
		return TeamResponse.teamResponseBuilder()
			.name(team.getName())
			.logo(team.getLogo())
			.code(team.getCode())
			.apiId(team.getApiId())
			.build();
	}
}

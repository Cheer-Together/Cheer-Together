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

	private Long id;
	private String leagueName;
	private String name;
	private String hanName;
	private String logo;
	private String code;
	private Long apiId;

	public static TeamResponse from(final Team team) {
		return TeamResponse.teamResponseBuilder()
			.id(team.getId())
			.leagueName(team.getLeague().getName())
			.name(team.getName())
			.hanName(team.getHanName())
			.logo(team.getLogo())
			.code(team.getCode())
			.apiId(team.getApiId())
			.build();
	}
}

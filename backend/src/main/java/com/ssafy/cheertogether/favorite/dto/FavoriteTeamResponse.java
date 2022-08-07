package com.ssafy.cheertogether.favorite.dto;

import com.ssafy.cheertogether.favorite.domain.FavoriteTeam;
import com.ssafy.cheertogether.team.dto.TeamResponse;

import lombok.Getter;

@Getter
public class FavoriteTeamResponse {
	private TeamResponse teamResponse;

	public FavoriteTeamResponse(FavoriteTeam favoriteTeam) {
		this.teamResponse = TeamResponse.from(favoriteTeam.getTeam());
	}
}

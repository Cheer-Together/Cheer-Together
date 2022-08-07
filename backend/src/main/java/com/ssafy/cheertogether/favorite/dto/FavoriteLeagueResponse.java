package com.ssafy.cheertogether.favorite.dto;

import com.ssafy.cheertogether.favorite.domain.FavoriteLeague;
import com.ssafy.cheertogether.league.dto.LeagueResponseExceptTeamList;

import lombok.Getter;

@Getter
public class FavoriteLeagueResponse {
	private LeagueResponseExceptTeamList leagueResponseExceptTeamList;

	public FavoriteLeagueResponse(FavoriteLeague favoriteLeague) {
		this.leagueResponseExceptTeamList = new LeagueResponseExceptTeamList(favoriteLeague.getLeague());
	}
}

package com.ssafy.cheertogether.member.dto;

import java.util.List;

import com.ssafy.cheertogether.favorite.domain.FavoriteLeague;
import com.ssafy.cheertogether.favorite.domain.FavoriteTeam;
import com.ssafy.cheertogether.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberLoginResponse {
	private final Long id;
	private final String email;
	private final String nickname;
	private final String profileImage;
	private final String role;
	private final String myInfo;
	private final List<FavoriteLeague> favoriteLeagueList;
	private final List<FavoriteTeam> favoriteTeamList;
	private String jwtToken;

	public MemberLoginResponse(Member member) {
		id = member.getId();
		email = member.getEmail();
		nickname = member.getNickname();
		profileImage = member.getNickname();
		role = member.getRole();
		myInfo = member.getMyInfo();
		favoriteLeagueList = member.getFavoriteLeagueList();
		favoriteTeamList = member.getFavoriteTeamList();
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}

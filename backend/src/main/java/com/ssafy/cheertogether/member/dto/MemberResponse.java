package com.ssafy.cheertogether.member.dto;

import java.util.List;

import com.ssafy.cheertogether.favorite.domain.FavoriteLeague;
import com.ssafy.cheertogether.favorite.domain.FavoriteTeam;
import com.ssafy.cheertogether.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberResponse {
	private String email;
	private String nickname;
	private String profileImage;
	private String role;
	private String myInfo;
	private List<FavoriteLeague> favoriteLeagueList;
	private List<FavoriteTeam> favoriteTeamList;

	public MemberResponse(Member member) {
		email = member.getEmail();
		nickname = member.getNickname();
		profileImage = member.getProfileImage();
		role = member.getRole();
		myInfo = member.getMyInfo();
		favoriteLeagueList = member.getFavoriteLeagueList();
		favoriteTeamList = member.getFavoriteTeamList();
	}
}

package com.ssafy.cheertogether.member.dto;

import java.util.List;

import com.ssafy.cheertogether.favorite.domain.FavoriteLeague;
import com.ssafy.cheertogether.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberResponse {
	private final String email;
	private final String nickname;
	private final String profileImage;
	private final String role;
	private final String myInfo;
	private final List<FavoriteLeague> favoriteLeagueList;

	public MemberResponse(Member member) {
		email = member.getEmail();
		nickname = member.getNickname();
		profileImage = member.getProfileImage();
		role = member.getRole();
		myInfo = member.getMyInfo();
		favoriteLeagueList = member.getFavoriteLeagueList();
	}
}

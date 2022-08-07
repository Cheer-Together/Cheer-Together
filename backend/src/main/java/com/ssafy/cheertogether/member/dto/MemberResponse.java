package com.ssafy.cheertogether.member.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.cheertogether.favorite.dto.FavoriteLeagueResponse;
import com.ssafy.cheertogether.favorite.dto.FavoriteTeamResponse;
import com.ssafy.cheertogether.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberResponse {
	private final String email;
	private final String nickname;
	private final String profileImage;
	private final String role;
	private final String myInfo;
	private final List<FavoriteLeagueResponse> favoriteLeagueResponseList = new ArrayList<>();
	private final List<FavoriteTeamResponse> favoriteTeamResponseList = new ArrayList<>();

	public MemberResponse(Member member) {
		email = member.getEmail();
		nickname = member.getNickname();
		profileImage = member.getProfileImage();
		role = member.getRole();
		myInfo = member.getMyInfo();
		favoriteLeagueResponseList.addAll(
			member.getFavoriteLeagueList()
				.stream()
				.map(favoriteLeague -> new FavoriteLeagueResponse(favoriteLeague))
				.collect(Collectors.toList())
		);
		favoriteTeamResponseList.addAll(
			member.getFavoriteTeamList()
				.stream()
				.map(favoriteTeam -> new FavoriteTeamResponse(favoriteTeam))
				.collect(Collectors.toList())
		);
	}
}

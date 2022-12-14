package com.ssafy.cheertogether.member.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.cheertogether.league.dto.LeagueResponseExceptTeamList;
import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.domain.Role;
import com.ssafy.cheertogether.team.dto.TeamResponse;

import lombok.Getter;

@Getter
public class MemberResponse {
	private final Long id;
	private final String email;
	private final String nickname;
	private final Role role;
	private final String myInfo;
	private final Integer point;
	private final List<LeagueResponseExceptTeamList> favoriteLeagueList = new ArrayList<>();
	private final List<TeamResponse> favoriteTeamList = new ArrayList<>();

	public MemberResponse(Member member) {
		id = member.getId();
		email = member.getEmail();
		nickname = member.getNickname();
		role = member.getRole();
		myInfo = member.getMyInfo();
		point = member.getPoint();
		favoriteLeagueList.addAll(
			member.getFavoriteLeagueList()
				.stream()
				.map(favoriteLeague -> new LeagueResponseExceptTeamList(favoriteLeague.getLeague()))
				.collect(Collectors.toList())
		);
		favoriteTeamList.addAll(
			member.getFavoriteTeamList()
				.stream()
				.map(favoriteTeam -> TeamResponse.from(favoriteTeam.getTeam()))
				.collect(Collectors.toList())
		);
	}
}

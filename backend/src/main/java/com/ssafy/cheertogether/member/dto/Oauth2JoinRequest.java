package com.ssafy.cheertogether.member.dto;

import java.util.List;

import com.ssafy.cheertogether.member.domain.Role;

import lombok.Getter;

@Getter
public class Oauth2JoinRequest {
	private String email;
	private String nickname;
	private Role role;
	private String myInfo;
	private List<Long> favoriteLeagueList;
	private List<Long> favoriteTeamList;
}

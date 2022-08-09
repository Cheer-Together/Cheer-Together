package com.ssafy.cheertogether.member.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class Oauth2JoinRequest {
	private String email;
	private String nickname;
	private String profileImage;
	private String role;
	private String myInfo;
	private List<Integer> favoriteLeagueList;
	private List<Integer> favoriteTeamList;
}

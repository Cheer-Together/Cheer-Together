package com.ssafy.cheertogether.member.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class MemberModifyRequest {
	private String nickname;
	private String password;
	private String profileImage;
	private String myInfo;
	private List<Long> favoriteLeagueList;
	private List<Long> favoriteTeamList;
}

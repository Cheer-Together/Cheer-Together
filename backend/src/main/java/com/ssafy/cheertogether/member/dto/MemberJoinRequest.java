package com.ssafy.cheertogether.member.dto;

import java.util.List;

import javax.annotation.Nullable;

import com.ssafy.cheertogether.member.domain.Role;

import lombok.Getter;

@Getter
public class MemberJoinRequest {
	private String email;
	private String nickname;
	private String password;
	private Role role;
	private String myInfo;
	private List<Long> favoriteLeagueList;
	private List<Long> favoriteTeamList;
}

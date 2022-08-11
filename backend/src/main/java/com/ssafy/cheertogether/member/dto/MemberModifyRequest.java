package com.ssafy.cheertogether.member.dto;

import java.util.List;

import javax.annotation.Nullable;

import lombok.Getter;

@Getter
public class MemberModifyRequest {
	private String nickname;
	@Nullable
	private String profileImage;
	private String myInfo;
	private List<Long> favoriteLeagueList;
	private List<Long> favoriteTeamList;

	public void saveProfileImgLink(String link) {
		profileImage = link;
	}
}

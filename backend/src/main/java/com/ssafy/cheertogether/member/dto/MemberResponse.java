package com.ssafy.cheertogether.member.dto;

import com.ssafy.cheertogether.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberResponse {
	private String email;
	private String nickname;
	private String profileImage;
	private String role;
	private String myInfo;

	public MemberResponse(Member member) {
		 email = member.getEmail();
		 nickname = member.getNickname();
		 profileImage = member.getProfileImage();
		 role = member.getRole();
		 myInfo = member.getMyInfo();
	}
}

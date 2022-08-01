package com.ssafy.cheertogether.member.dto;

import lombok.Getter;

@Getter
public class MemberModifyRequest {
	private String nickname;
	private String password;
	private String profileImage;
	private String myInfo;
}

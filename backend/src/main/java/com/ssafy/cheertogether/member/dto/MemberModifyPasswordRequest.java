package com.ssafy.cheertogether.member.dto;

import lombok.Getter;

@Getter
public class MemberModifyPasswordRequest {
	private String curPassword;
	private String newPassword;
}

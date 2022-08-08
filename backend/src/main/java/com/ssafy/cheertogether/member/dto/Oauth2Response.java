package com.ssafy.cheertogether.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Oauth2Response {
	private boolean isNewMember;
	private String token;
	private String email;
}

package com.ssafy.cheertogether.member.dto;

import com.ssafy.cheertogether.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberPointRankingResponse {
	private final Long id;
	private final String email;
	private final String nickname;
	private final Integer point;

	public MemberPointRankingResponse(Member member) {
		this.id = member.getId();
		this.email = member.getEmail();
		this.nickname = member.getNickname();
		this.point = member.getPoint();
	}
}

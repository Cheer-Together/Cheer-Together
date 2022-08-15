package com.ssafy.cheertogether.favorite.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.team.domain.Team;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class FavoriteTeam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@JsonBackReference
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	@Builder
	private FavoriteTeam(Member member, Team team, Integer priority) {
		this.member = member;
		this.team = team;
	}

	public static FavoriteTeam from(Member member, Team team) {
		return FavoriteTeam.builder().member(member).team(team).build();
	}

}

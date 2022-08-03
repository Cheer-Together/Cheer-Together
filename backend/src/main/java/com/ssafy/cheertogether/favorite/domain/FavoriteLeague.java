package com.ssafy.cheertogether.favorite.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.cheertogether.league.domain.League;
import com.ssafy.cheertogether.league.repository.LeagueRepository;
import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.dto.MemberJoinRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class FavoriteLeague {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@JsonBackReference
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "league_id")
	private League league;

	@Builder
	private FavoriteLeague(Member member, League league) {
		this.member = member;
		this.league = league;
	}

	public static FavoriteLeague from (Member member, League league) {
		return FavoriteLeague.builder()
			.member(member)
			.league(league)
			.build();
	}

}

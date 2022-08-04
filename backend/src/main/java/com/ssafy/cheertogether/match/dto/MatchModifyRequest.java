package com.ssafy.cheertogether.match.dto;

import java.time.LocalDateTime;

import com.ssafy.cheertogether.match.domain.MatchStatus;

import lombok.Getter;

@Getter
public class MatchModifyRequest {
	private LocalDateTime kickoff;
	private String stadium;
	private MatchStatus status;
	private Integer homeScore;
	private Integer awayScore;
	private Long apiId;
}

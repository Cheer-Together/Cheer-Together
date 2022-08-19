package com.ssafy.cheertogether.game.dto;

import com.ssafy.cheertogether.game.domain.Assist;
import com.ssafy.cheertogether.game.domain.GameDetail;
import com.ssafy.cheertogether.game.domain.Player;
import com.ssafy.cheertogether.game.domain.Time;
import com.ssafy.cheertogether.team.domain.Team;

import lombok.Getter;

@Getter
public class GameDetailResponse {
	private final Time time;
	private final Team team;
	private final Player player;
	private final Assist assist;
	private final String type;
	private final String detail;
	private final String comments;

	public GameDetailResponse(GameDetail gameDetail) {
		this.time = gameDetail.getTime();
		this.team = gameDetail.getTeam();
		this.player = gameDetail.getPlayer();
		this.assist = gameDetail.getAssist();
		this.type = gameDetail.getType();
		this.detail = gameDetail.getDetail();
		this.comments = gameDetail.getComments();
	}
}

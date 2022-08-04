package com.ssafy.cheertogether.cheeringsong.dto;

import com.ssafy.cheertogether.cheeringsong.domain.CheeringSong;

import lombok.Getter;

@Getter
public class CheeringSongResponse {
	private final Long id;
	private final Long team_id;
	private final String target;
	private final String name;
	private final String file;

	public CheeringSongResponse(CheeringSong cheeringSong) {
		id = cheeringSong.getId();
		team_id = cheeringSong.getTeam().getId();
		target = cheeringSong.getTarget();
		name = cheeringSong.getName();
		file = cheeringSong.getFile();
	}
}

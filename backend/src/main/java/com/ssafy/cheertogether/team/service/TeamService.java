package com.ssafy.cheertogether.team.service;

import static com.ssafy.cheertogether.team.TeamConstant.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.team.domain.Team;
import com.ssafy.cheertogether.team.dto.TeamResponse;
import com.ssafy.cheertogether.team.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

	private final TeamRepository teamRepository;

	/**
	 * 팀 api id를 통한 팀 정보 조회
	 */
	public TeamResponse findByApiId(Long apiId) {
		Team team = teamRepository.findTeamByApiId(apiId)
			.orElseThrow(() -> new IllegalArgumentException(MISMATCH_APIID_ERROR_MESSAGE));
		return TeamResponse.from(team);
	}
}

package com.ssafy.cheertogether.league.service;

import static com.ssafy.cheertogether.league.LeagueConstant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.league.domain.League;
import com.ssafy.cheertogether.league.dto.LeagueResponse;
import com.ssafy.cheertogether.league.repository.LeagueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeagueService {

	private final LeagueRepository leagueRepository;

	/**
	 * 리그 전부 조회
	 */
	public List<LeagueResponse> findAll() {
		List<League> leagueList = leagueRepository.findAll();
		List<LeagueResponse> leagueResponseList = new ArrayList<>();
		leagueResponseList.addAll(leagueList.stream().map(LeagueResponse::from).collect(Collectors.toList()));
		return leagueResponseList;
	}

	/**
	 * 리그 api id를 통한 리그 정보 조회
	 */
	public LeagueResponse findByApiId(int apiId) {
		leagueRepository.findLeagueByApiId(apiId)
			.orElseThrow(() -> new IllegalArgumentException(MISMATCH_APIID_ERROR_MESSAGE));
		return LeagueResponse.from(leagueRepository.findLeagueByApiId(apiId).get());
	}
}

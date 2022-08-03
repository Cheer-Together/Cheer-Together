package com.ssafy.cheertogether.match.service;

import static com.ssafy.cheertogether.match.MatchConstant.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.match.domain.Match;
import com.ssafy.cheertogether.match.dto.MatchModifyRequest;
import com.ssafy.cheertogether.match.dto.MatchResponse;
import com.ssafy.cheertogether.match.repository.MatchRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchService {
	private final MatchRepository matchRepository;

	public void update(Long id, MatchModifyRequest matchModifyRequest) {
		Match match = matchRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCH_ERROR_MESSAGE));
		match.updateMatchInfos(matchModifyRequest);
	}

	@Transactional(readOnly = true)
	public List<MatchResponse> findMatches() {
		return matchRepository.findAll().stream()
			.map(MatchResponse::new)
			.collect(Collectors.toList());
	}
}

package com.ssafy.cheertogether.game.service;

import static com.ssafy.cheertogether.game.GameConstant.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.game.domain.Game;
import com.ssafy.cheertogether.game.dto.GameModifyRequest;
import com.ssafy.cheertogether.game.dto.GameResponse;
import com.ssafy.cheertogether.game.repository.GameRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {
	private final GameRepository matchRepository;

	public void update(Long id, GameModifyRequest gameModifyRequest) {
		Game game = matchRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCH_ERROR_MESSAGE));
		game.updateGameInfos(gameModifyRequest);
	}

	@Transactional(readOnly = true)
	public List<GameResponse> findMatches() {
		return matchRepository.findAll().stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}
}

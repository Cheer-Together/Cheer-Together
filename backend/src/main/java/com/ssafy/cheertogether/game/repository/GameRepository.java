package com.ssafy.cheertogether.game.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.cheertogether.game.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
	Optional<Game> findGameById(Long id);

	List<Game> findAllByLeagueApiId(Long leagueApiId);

	List<Game> findAllByLeagueApiIdAndKickoffBetween(Long leagueApiId, LocalDateTime start, LocalDateTime end);
}

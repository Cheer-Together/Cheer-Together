package com.ssafy.cheertogether.game.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.game.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
	Optional<Game> findGameById(Long id);
}

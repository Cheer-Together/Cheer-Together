package com.ssafy.cheertogether.game.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.game.domain.GameDetail;

public interface GameDetailRepository extends JpaRepository<GameDetail, Long> {
	List<GameDetail> findAllByGameId(Long gameId);
}

package com.ssafy.cheertogether.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.match.domain.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
}

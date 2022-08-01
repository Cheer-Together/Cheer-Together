package com.ssafy.cheertogether.league.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.league.domain.League;

public interface LeagueRepository extends JpaRepository<League, Long> {

	League findLeagueByApiId(int apiId);

}

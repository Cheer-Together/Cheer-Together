package com.ssafy.cheertogether.team.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.team.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Optional<Team> findTeamByApiId(Long apiId);
}

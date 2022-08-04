package com.ssafy.cheertogether.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.favorite.domain.FavoriteTeam;

public interface FavoriteTeamRepository extends JpaRepository<FavoriteTeam, Long> {
	void deleteFavoriteTeamByMember_Id(Long memberId);
}

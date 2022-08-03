package com.ssafy.cheertogether.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.favorite.domain.FavoriteLeague;

public interface FavoriteLeagueRepository extends JpaRepository<FavoriteLeague, Long> {
	void deleteFavoriteLeagueByMember_Email(String memberEmail);
}

package com.ssafy.cheertogether.cheeringsong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.cheeringsong.domain.CheeringSong;

public interface CheeringSongRepository extends JpaRepository<CheeringSong, Long> {
	List<CheeringSong> findAllByTeam_Id(Long team_id);
}

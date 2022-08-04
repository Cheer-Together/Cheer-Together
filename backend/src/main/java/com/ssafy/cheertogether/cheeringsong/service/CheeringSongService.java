package com.ssafy.cheertogether.cheeringsong.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.cheeringsong.dto.CheeringSongResponse;
import com.ssafy.cheertogether.cheeringsong.repository.CheeringSongRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CheeringSongService {
	private final CheeringSongRepository cheeringSongRepository;

	public List<CheeringSongResponse> findCheeringSongs(Long teamId) {
		return cheeringSongRepository.findAllByTeam_Id(teamId).stream()
			.map(CheeringSongResponse::new)
			.collect(Collectors.toList());
	}
}

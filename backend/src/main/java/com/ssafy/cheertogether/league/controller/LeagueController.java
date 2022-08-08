package com.ssafy.cheertogether.league.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.league.dto.LeagueResponse;
import com.ssafy.cheertogether.league.dto.LeagueResponseExceptTeamList;
import com.ssafy.cheertogether.league.service.LeagueService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/leagues")
@RequiredArgsConstructor
@Slf4j
public class LeagueController {

	private final LeagueService leagueService;

	@GetMapping
	@ApiOperation(value = "모든 리그 정보 조회", notes = "모든 리그 정보 조회")
	public ResponseEntity<List<LeagueResponse>> findAll() {
		return new ResponseEntity<>(leagueService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/noTeam")
	@ApiOperation(value = "모든 리그 정보 팀 정보 제외 조회", notes = "모든 리그 정보 팀 정보 제외 조회")
	public ResponseEntity<List<LeagueResponseExceptTeamList>> findAllExceptTeam() {
		return new ResponseEntity<>(leagueService.findAlleExceptTeam(), HttpStatus.OK);
	}

	@GetMapping("/{apiId}")
	@ApiOperation(value = "리그 정보 단건 조회", notes = "apiId를 통한 리그 정보 단건 조회")
	public ResponseEntity<LeagueResponse> findByApiId(
		@ApiParam(value = "apiId", required = true, example = "39") @PathVariable Long apiId) {
		return new ResponseEntity<>(leagueService.findByApiId(apiId), HttpStatus.OK);
	}

}

package com.ssafy.cheertogether.team.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.team.dto.TeamResponse;
import com.ssafy.cheertogether.team.service.TeamService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@Slf4j
public class TeamController {

	private final TeamService teamService;

	@GetMapping("/{apiId}")
	@ApiOperation(value = "팀 정보 단건 조회", notes = "apiId를 통한 팀 정보 단건 조회")
	public ResponseEntity<TeamResponse> findByApiId(
		@ApiParam(value = "apiId", required = true, example = "47") @PathVariable int apiId) {
		return new ResponseEntity<>(teamService.findByApiId(apiId), HttpStatus.OK);
	}
}

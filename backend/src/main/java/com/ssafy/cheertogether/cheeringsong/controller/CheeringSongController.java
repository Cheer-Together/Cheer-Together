package com.ssafy.cheertogether.cheeringsong.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.cheeringsong.dto.CheeringSongResponse;
import com.ssafy.cheertogether.cheeringsong.service.CheeringSongService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cheeringsong")
@RequiredArgsConstructor
public class CheeringSongController {
	private final CheeringSongService cheeringSongService;

	@GetMapping("/{teamId}")
	@ApiOperation(value = "응원가 목록 조회", notes = "특정 팀의 응원가 목록 조회")
	public ResponseEntity<List<CheeringSongResponse>> findCheeringSongs(
		@PathVariable @ApiParam(value = "팀 아이디", required = true, example = "1") Long teamId) {
		return new ResponseEntity<>(cheeringSongService.findCheeringSongs(teamId), HttpStatus.OK);
	}
}

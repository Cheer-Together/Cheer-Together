package com.ssafy.cheertogether.match.controller;

import static com.ssafy.cheertogether.match.MatchConstant.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.match.dto.MatchModifyRequest;
import com.ssafy.cheertogether.match.dto.MatchResponse;
import com.ssafy.cheertogether.match.service.MatchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {
	private final MatchService matchService;

	@GetMapping
	public ResponseEntity<List<MatchResponse>> findMatches() {
		return new ResponseEntity<>(matchService.findMatches(), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> modify(@PathVariable Long id, MatchModifyRequest matchModifyRequest) {
		matchService.update(id, matchModifyRequest);
		return new ResponseEntity<>(MODIFY_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}
}

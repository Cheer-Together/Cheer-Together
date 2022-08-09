package com.ssafy.cheertogether.game.controller;

import static com.ssafy.cheertogether.game.GameConstant.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.game.dto.GameModifyRequest;
import com.ssafy.cheertogether.game.dto.GameResponse;
import com.ssafy.cheertogether.game.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
	private final GameService gameService;

	@GetMapping
	public ResponseEntity<List<GameResponse>> findGames() {
		return new ResponseEntity<>(gameService.findGames(), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> modify(@PathVariable Long id, GameModifyRequest gameModifyRequest) {
		gameService.update(id, gameModifyRequest);
		return new ResponseEntity<>(MODIFY_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}
}

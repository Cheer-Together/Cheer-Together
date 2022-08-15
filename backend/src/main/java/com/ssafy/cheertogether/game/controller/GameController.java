package com.ssafy.cheertogether.game.controller;

import static com.ssafy.cheertogether.game.GameConstant.*;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.cheertogether.game.dto.GameApiParameterRequest;
import com.ssafy.cheertogether.game.dto.GameResponse;
import com.ssafy.cheertogether.game.service.GameService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
	private final GameService gameService;

	@GetMapping
	@ApiOperation(value = "모든 경기 정보 조회", notes = "모든 경기 정보 조회\n" + "Status 관련 설명\n" + "TBD : 경기 일정 미정\n"
		+ "NS : 경기 시작 전\n" + "LIVE : 경기 진행 중\n" + "FT : 경기 종료\n")
	public ResponseEntity<List<GameResponse>> findGames() {
		return new ResponseEntity<>(gameService.findGames(), HttpStatus.OK);
	}

	@GetMapping("/game/{id}")
	@ApiOperation(value = "경기 정보 단건 검색", notes = "경기 아이디로 경기정보 검색")
	public ResponseEntity<GameResponse> findGameById(
		@ApiParam(value = "경기 ID", required = true, example = "31") @PathVariable Long id) {
		return new ResponseEntity<>(gameService.findGameById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "해당 경기 정보 업데이트", notes = "해당 경기 정보 업데이트")
	public ResponseEntity<GameResponse> modify (@PathVariable Long id,
		@ApiParam(value = "경기 api Id", required = true, example = "867946") @RequestParam String apiId) {
		GameResponse gameResponse = null;
		try {
			gameResponse = gameService.update(id, apiId);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<>(gameResponse, HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "경기 정보 삽입", notes = "리그와 시즌 별 경기 정보 삽입")
	public ResponseEntity<String> insert(@RequestBody GameApiParameterRequest gameApiParameterRequest) {
		String leagueApiId = gameApiParameterRequest.getLeagueApiId();
		String season = gameApiParameterRequest.getSeason();
		try {
			gameService.insert(leagueApiId, season);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<>(REGIST_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@GetMapping("/{leagueApiId}")
	@ApiOperation(value = "리그별 경기 정보 조회", notes = "리그별 경기 정보 조회\n" + "Status 관련 설명\n" + "TBD : 경기 일정 미정\n"
		+ "NS : 경기 시작 전\n" + "LIVE : 경기 진행 중\n" + "FT : 경기 종료\n")
	public ResponseEntity<List<GameResponse>> findGamesByLeagueApiId(@PathVariable Long leagueApiId) {
		return new ResponseEntity<>(gameService.findGamesByLeagueApiId(leagueApiId), HttpStatus.OK);
	}

	@GetMapping("/{leagueApiId}/date")
	@ApiOperation(value = "리그와 날짜 별 경기 정보 조회", notes = "리그와 날짜 별 경기 정보 조회\n" + "Status 관련 설명\n" + "TBD : 경기 일정 미정\n"
		+ "NS : 경기 시작 전\n" + "LIVE : 경기 진행 중\n" + "FT : 경기 종료\n")
	public ResponseEntity<List<GameResponse>> findGamesByLeagueApiIdAndDateDay(@PathVariable Long leagueApiId,
		@ApiParam(value = "연월일", required = true, example = "yyyyMMdd") @RequestParam String date) {
		return new ResponseEntity<>(gameService.findGamesByLeagueApiIdAndDay(leagueApiId, date), HttpStatus.OK);
	}

	@GetMapping("/{leagueApiId}/month")
	@ApiOperation(value = "리그와 월 별 경기 정보 조회", notes = "리그와 월 별 경기 정보 조회\n" + "Status 관련 설명\n" + "TBD : 경기 일정 미정\n"
		+ "NS : 경기 시작 전\n" + "LIVE : 경기 진행 중\n" + "FT : 경기 종료\n")
	public ResponseEntity<List<GameResponse>> findGamesByLeagueApiIdAndMonth(@PathVariable Long leagueApiId,
		@ApiParam(value = "연월", required = true, example = "yyyy-MM") @RequestParam String date) {
		return new ResponseEntity<>(gameService.findGamesByLeagueApiIdAndMonth(leagueApiId, date), HttpStatus.OK);
	}

	@GetMapping("/live")
	@ApiOperation(value = "진행중인 경기 정보 조회", notes = "진행중인 경기 정보 조회")
	public ResponseEntity<List<GameResponse>> findLiveGames() {
		return new ResponseEntity<>(gameService.findGamesByStatus(), HttpStatus.OK);
	}

	@GetMapping("/live/{leagueApiId}")
	@ApiOperation(value = "진행중인 리그 별 경기 정보 조회", notes = "진행중인 리그 별 경기 정보 조회")
	public ResponseEntity<List<GameResponse>> findLiveGamesByLeagueApiId(@PathVariable Long leagueApiId) {
		return new ResponseEntity<>(gameService.findGamesByLeagueApiIdAndStatus(leagueApiId), HttpStatus.OK);
	}

	@GetMapping("/date")
	@ApiOperation(value = "날짜 별 경기 정보 조회", notes = "날짜 별 경기 정보 조회\n"
		+ "Status 관련 설명\n"
		+ "TBD : 경기 일정 미정\n"
		+ "NS : 경기 시작 전\n"
		+ "LIVE : 경기 진행 중\n"
		+ "FT : 경기 종료\n")
	public ResponseEntity<List<GameResponse>> findGamesByLeagueApiIdAndDateDay(@ApiParam(value = "연월일", required = true, example = "yyyyMMdd") @RequestParam String date) {
		return new ResponseEntity<>(gameService.findGamesByDay(date), HttpStatus.OK);
	}
}

package com.ssafy.cheertogether.room.controller;

import static com.ssafy.cheertogether.room.RoomConstant.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.room.dto.RoomCreateRequest;
import com.ssafy.cheertogether.room.dto.RoomModifyRequest;
import com.ssafy.cheertogether.room.dto.RoomResponse;
import com.ssafy.cheertogether.room.repository.RoomRepository;
import com.ssafy.cheertogether.room.service.RoomService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

	private final RoomService roomService;

	@GetMapping("/")
	@ApiOperation(value = "모든 응원방 검색", notes = "모든 응원방 목록 검색")
	public ResponseEntity<List<RoomResponse>> findAll() {
		return new ResponseEntity<>(roomService.findRooms(), HttpStatus.OK);
	}

	@GetMapping("/{roomId}")
	@ApiOperation(value = "응원방 아이디 검색", notes = "응원방 아이디로 검색")
	public ResponseEntity<RoomResponse> findRoomById(
		@ApiParam(value = "응원방 아이디", required = true, example = "1") @PathVariable Long roomId) {
		return new ResponseEntity<>(roomService.findRoomById(roomId), HttpStatus.OK);
	}

	@GetMapping("/game/{gameId}")
	@ApiOperation(value = "경기 아이디 검색", notes = "특정 경기 시청중인 응원방 검색")
	public ResponseEntity<List<RoomResponse>> findRoomByGameId(
		@ApiParam(value = "경기 아이디", required = true, example = "1") @PathVariable Long gameId) {
		return new ResponseEntity<>(roomService.findRoomByGameId(gameId), HttpStatus.OK);
	}

	@GetMapping("session/{sessionId}")
	@ApiOperation(value = "응원방 세션 검색", notes = "응원방 세션 아이디로 검색")
	public ResponseEntity<RoomResponse> findRoomBySessionId(
		@ApiParam(value = "응원방 세션 아이디", required = true) @PathVariable String sessionId) {
		return new ResponseEntity<>(roomService.findRoomBySessionId(sessionId), HttpStatus.OK);
	}

	@GetMapping("/league/{leagueApiId}")
	@ApiOperation(value = "리그 아이디로 검색", notes = "특정 리그의 경기 시청중인 응원방 검색(프리미어리그: 39, 라리가: 140, 세리에A: 135, 분데스리가: 78, 리그앙: 61, k리그: 292)")
	public ResponseEntity<List<RoomResponse>> findRoomByLeagueApiId(
		@ApiParam(value = "리그 아이디", required = true, example = "1") @PathVariable Long leagueApiId) {
		return new ResponseEntity<>(roomService.findRoomByLeague(leagueApiId), HttpStatus.OK);
	}

	@PostMapping("/")
	@ApiOperation(value = "응원방 생성", notes = "응원방 생성")
	public ResponseEntity<String> createRoom(
		@ApiParam(value = "응원방 정보", required = true) @RequestBody RoomCreateRequest roomCreateRequest) {
		roomService.createRoom(roomCreateRequest);
		return new ResponseEntity<>(CREATE_ROOM_SUCCESS_MESSAGE, HttpStatus.OK);
	}

	@PutMapping("/")
	@ApiOperation(value = "응원방 정보 수정", notes = "방장의 응원방 정보 수정")
	public ResponseEntity<String> modifyRoom(
		@ApiParam(value = "응원방 수정 정보", required = true) @RequestBody RoomModifyRequest roomModifyRequest) {
		roomService.modifyRoom(roomModifyRequest);
		return new ResponseEntity<>(MODIFY_ROOM_SUCCESS_MESSAGE, HttpStatus.OK);
	}

	@DeleteMapping("/{roomId}")
	@ApiOperation(value = "응원방 정보 삭제", notes = "응원방 응원 종료로 인한 응원방 삭제")
	public ResponseEntity<String> deleteRoom(
		@ApiParam(value = "삭제할 응원방 아이디", required = true, example = "1") @PathVariable Long roomId) {
		roomService.deleteRoom(roomId);
		return new ResponseEntity<>(DELETE_ROOM_SUCCESS_MESSAGE, HttpStatus.OK);
	}

	@GetMapping("/search/")
	@ApiOperation(value = "응원방 검색", notes = "특정 키워드를 방이름, 방장 아이디에 포함하고 있는 모든 응원방 검색")
	public ResponseEntity<List<RoomResponse>> findRoomByNameContaining(
		@ApiParam(value = "검색하고 싶은 위치(방이름=type, 방장아이디=managerId)", required = true, example = "name") @RequestParam(value = "type") String type,
		@ApiParam(value = "검색하고 싶은 키워드", required = true, example = "손흥민") @RequestParam(value = "keyword") String keyword) {
		return new ResponseEntity<>(roomService.findRoomByContaining(type, keyword), HttpStatus.OK);
	}
}

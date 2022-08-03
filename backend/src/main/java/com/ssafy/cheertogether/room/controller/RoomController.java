package com.ssafy.cheertogether.room.controller;

import static com.ssafy.cheertogether.room.RoomConstant.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.room.dto.RoomCreateRequest;
import com.ssafy.cheertogether.room.dto.RoomModifyRequest;
import com.ssafy.cheertogether.room.dto.RoomResponse;
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

	@GetMapping("/match/{matchId}")
	@ApiOperation(value = "매치 아이디 검색", notes = "매치 아이디 검색")
	public ResponseEntity<RoomResponse> findRoomByMatchId(
		@ApiParam(value = "매치 아이디", required = true, example = "1") @PathVariable Long matchId) {
		return new ResponseEntity<>(roomService.findRoomByMatchId(matchId), HttpStatus.OK);
	}

	@PostMapping("/")
	@ApiOperation(value = "응원방 생성", notes = "응원방 생성")
	public ResponseEntity<String> createRoom(
		@ApiParam(value = "응원방 정보", required = true)
		@RequestBody RoomCreateRequest roomCreateRequest) {
		roomService.createRoom(roomCreateRequest);
		return new ResponseEntity<>(CREATE_ROOM_SUCCESS_MESSAGE, HttpStatus.OK);
	}

	@PutMapping("/")
	@ApiOperation(value = "응원방 정보 수정", notes = "방장의 응원방 정보 수정")
	public ResponseEntity<String> modifyRoom(
		@ApiParam(value = "응원방 수정 정보", required = true)
		@RequestBody RoomModifyRequest roomModifyRequest) {
		roomService.modifyRoom(roomModifyRequest);
		return new ResponseEntity<>(MODIFY_ROOM_SUCCESS_MESSAGE, HttpStatus.OK);
	}
}

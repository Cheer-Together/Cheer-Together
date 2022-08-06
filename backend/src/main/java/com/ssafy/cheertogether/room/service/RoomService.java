package com.ssafy.cheertogether.room.service;

import static com.ssafy.cheertogether.room.RoomConstant.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.game.domain.Game;
import com.ssafy.cheertogether.game.repository.GameRepository;
import com.ssafy.cheertogether.room.domain.Room;
import com.ssafy.cheertogether.room.dto.RoomCreateRequest;
import com.ssafy.cheertogether.room.dto.RoomModifyRequest;
import com.ssafy.cheertogether.room.dto.RoomResponse;
import com.ssafy.cheertogether.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

	private final RoomRepository roomRepository;
	private final GameRepository gameRepository;

	@Transactional(readOnly = true)
	public List<RoomResponse> findRooms() {
		List<Room> roomList = roomRepository.findAll();
		return roomList.stream().map(RoomResponse::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public RoomResponse findRoomById(Long id) {
		Room room = roomRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(MISMATCH_ROOM_ID_ERROR_MESSAGE));
		return new RoomResponse(room);
	}

	@Transactional(readOnly = true)
	public List<RoomResponse> findRoomByMatchId(Long matchId) {
		Game match = gameRepository.findGameById(matchId)
			.orElseThrow(() -> new IllegalArgumentException(MISMATCH_MATCH_ID_ERROR_MESSAGE));
		return match.getRoomList().stream().map(RoomResponse::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<RoomResponse> findRoomByContaining(String type, String keyword) {
		if (type.equals("name")) {
			List<Room> roomList = roomRepository.findRoomByNameContaining(keyword);
			return roomList.stream().map(RoomResponse::new).collect(Collectors.toList());
		} else if (type.equals("managerId")) {
			List<Room> roomList = roomRepository.findRoomByManagerIdContaining(keyword);
			return roomList.stream().map(RoomResponse::new).collect(Collectors.toList());
		} else {
			throw new IllegalStateException(MISMATCH_SEARCH_TYPE_ERROR_MESSAGE);
		}
	}

	public void createRoom(RoomCreateRequest roomCreateRequest) {
		roomRepository.save(Room.from(roomCreateRequest));
	}

	public void modifyRoom(RoomModifyRequest modifyRequest) {
		Room room = roomRepository.findById(modifyRequest.getId())
			.orElseThrow(() -> new IllegalArgumentException(MODIFY_ROOM_ERROR_MESSAGE));
		room.update(modifyRequest);
	}
}

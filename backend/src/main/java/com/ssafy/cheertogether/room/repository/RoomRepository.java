package com.ssafy.cheertogether.room.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.room.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	Optional<Room> findRoomById(Long id);

	List<Room> findAll();

	@Transactional
	void deleteRoomById(Long id);

	List<Room> findRoomByNameContaining(String keyword);

	List<Room> findRoomByManagerIdContaining(String keyword);
}

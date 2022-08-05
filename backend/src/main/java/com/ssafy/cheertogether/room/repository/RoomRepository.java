package com.ssafy.cheertogether.room.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.room.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	Optional<Room> findById(Long id);

	List<Room> findAll();

	@Transactional
	void deleteRoomById(Long id);

}

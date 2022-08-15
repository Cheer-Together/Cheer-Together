package com.ssafy.cheertogether.game;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.cheertogether.game.service.GameService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GameSchedule {

	private final GameService gameService;

	@Scheduled(cron = "0 0/15 * * * *")
	public void gameLiveUpdate(){
		gameService.updateGameLive(LocalDateTime.now());
	}

	@Scheduled(cron = "0 0/5 * * * *")
	public void liveGameUpdate(){
		// gameService.updateLiveGames();
	}

}

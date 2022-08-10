package com.ssafy.cheertogether.game.service;

import static com.ssafy.cheertogether.game.GameConstant.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.game.domain.Game;
import com.ssafy.cheertogether.game.domain.GameStatus;
import com.ssafy.cheertogether.game.dto.GameModifyRequest;
import com.ssafy.cheertogether.game.dto.GameRegisterRequest;
import com.ssafy.cheertogether.game.dto.GameResponse;
import com.ssafy.cheertogether.game.repository.GameRepository;
import com.ssafy.cheertogether.team.domain.Team;
import com.ssafy.cheertogether.team.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {
	private final GameRepository gameRepository;
	private final TeamRepository teamRepository;

	public void update(Long id, String responseJson) throws ParseException {
		Game game = gameRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCH_ERROR_MESSAGE));
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(responseJson);
		JSONArray response = (JSONArray)jsonObject.get("response");
		JSONObject responseBody = (JSONObject)response.get(0);
		JSONObject fixture = (JSONObject)responseBody.get("fixture");
		JSONObject status = (JSONObject)fixture.get("status");
		JSONObject goals = (JSONObject)responseBody.get("goals");
		GameModifyRequest gameModifyRequest = new GameModifyRequest();
		gameModifyRequest.setApiId(game.getApiId());
		gameModifyRequest.setKickoff(game.getKickoff());
		gameModifyRequest.setStadium(game.getStadium());
		gameModifyRequest.setStatus(GameStatus.valueOf(status.get("short").toString()));
		gameModifyRequest.setHomeScore(Integer.parseInt(goals.get("home").toString()));
		gameModifyRequest.setAwayScore(Integer.parseInt(goals.get("away").toString()));
		game.updateGameInfos(gameModifyRequest);
	}

	@Transactional(readOnly = true)
	public List<GameResponse> findGames() {
		return gameRepository.findAll().stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}

	public void insert(String responseJson) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(responseJson);
		JSONArray response = (JSONArray)jsonObject.get("response");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
		for (int i = 0; i < response.size(); i++) {
			JSONObject responseBody = (JSONObject)response.get(i);
			JSONObject fixture = (JSONObject)responseBody.get("fixture");
			JSONObject venue = (JSONObject)fixture.get("venue");
			JSONObject status = (JSONObject)fixture.get("status");
			JSONObject league = (JSONObject)responseBody.get("league");
			JSONObject teams = (JSONObject)responseBody.get("teams");
			JSONObject home = (JSONObject)teams.get("home");
			JSONObject away = (JSONObject)teams.get("away");
			JSONObject goals = (JSONObject)responseBody.get("goals");
			GameRegisterRequest gameRegisterRequest = new GameRegisterRequest();
			gameRegisterRequest.setKickoff(LocalDateTime.parse(fixture.get("date").toString(), formatter));
			gameRegisterRequest.setStadium(venue.get("name").toString());
			gameRegisterRequest.setStatus(GameStatus.valueOf(status.get("short").toString()));
			if (goals.get("home") != null) {
				gameRegisterRequest.setHomeScore(Integer.parseInt(goals.get("home").toString()));
			}
			if (goals.get("away") != null) {
				gameRegisterRequest.setAwayScore(Integer.parseInt(goals.get("away").toString()));
			}
			gameRegisterRequest.setApiId(Long.parseLong(fixture.get("id").toString()));
			gameRegisterRequest.setLeagueApiId(Long.parseLong(league.get("id").toString()));
			Team homeTeam = teamRepository.findTeamByApiId(Long.parseLong(home.get("id").toString())).get();
			Team awayTeam = teamRepository.findTeamByApiId(Long.parseLong(away.get("id").toString())).get();
			Game game = Game.from(gameRegisterRequest, homeTeam, awayTeam);
			gameRepository.save(game);
		}
	}

	public List<GameResponse> findGamesByLeagueApiId(Long leagueApiId) {
		return gameRepository.findAllByLeagueApiId(leagueApiId)
			.stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}

	public List<GameResponse> findGamesByLeagueApiIdAndDay(Long leagueApiId, String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate dateTime = LocalDate.parse(date, formatter);
		LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(),
			0, 0);
		LocalDateTime end = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 23, 59);
		return gameRepository.findAllByLeagueApiIdAndKickoffBetween(leagueApiId, start, end)
			.stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}

	public List<GameResponse> findGamesByLeagueApiIdAndMonth(Long leagueApiId, String date) {
		YearMonth yearMonth = YearMonth.parse(date);
		LocalDateTime start = LocalDateTime.of(yearMonth.getYear(), yearMonth.getMonth(), 1, 0, 0);
		LocalDateTime end = LocalDateTime.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth(), 23,
			59);
		return gameRepository.findAllByLeagueApiIdAndKickoffBetween(leagueApiId, start, end)
			.stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}

	public void updateLiveGames(LocalDateTime localDateTime) {
		LocalDateTime kickoff = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute());
		List<Game> gameList = gameRepository.findAllByKickoff(kickoff);
		gameList.stream().forEach(game -> game.updateLive());
	}
}

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.cheertogether.game.domain.Game;
import com.ssafy.cheertogether.game.domain.GameStatus;
import com.ssafy.cheertogether.game.dto.GameApiParameterRequest;
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
	@Value("${spring.api.secretKey}")
	private String apiKey;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	public GameResponse findGameById(Long id){
		Game game = gameRepository.findGameById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_GAME_ERROR_MESSAGE));
		return GameResponse.from(game);
	}

	public GameResponse update(Long id, String apiId) throws ParseException {
		Game game = gameRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_GAME_ERROR_MESSAGE));
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("id", apiId);
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(apiResponseToJson(params));
		JSONArray response = (JSONArray)jsonObject.get("response");
		JSONObject responseBody = (JSONObject)response.get(0);
		JSONObject fixture = (JSONObject)responseBody.get("fixture");
		JSONObject status = (JSONObject)fixture.get("status");
		JSONObject goals = (JSONObject)responseBody.get("goals");
		GameModifyRequest gameModifyRequest = new GameModifyRequest();
		gameModifyRequest.setApiId(game.getApiId());
		gameModifyRequest.setKickoff(game.getKickoff());
		gameModifyRequest.setStadium(game.getStadium());
		try {
			gameModifyRequest.setStatus(GameStatus.valueOf(status.get("short").toString()));
			gameModifyRequest.setHomeScore(Integer.parseInt(goals.get("home").toString()));
			gameModifyRequest.setAwayScore(Integer.parseInt(goals.get("away").toString()));
		} catch (java.lang.IllegalArgumentException e) {
			gameModifyRequest.setStatus(game.getStatus());
			gameModifyRequest.setHomeScore(game.getHomeScore());
			gameModifyRequest.setAwayScore(game.getAwayScore());
		}
		game.updateGameInfos(gameModifyRequest);
		return GameResponse.from(game);
	}

	@Transactional(readOnly = true)
	public List<GameResponse> findGames() {
		return gameRepository.findAll().stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}

	public void insert(String leagueApiId, String season) throws ParseException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("league", leagueApiId);
		params.add("season", season);
		params.add("timezone", "Asia/Seoul");
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(apiResponseToJson(params));
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

	@Transactional(readOnly = true)
	public List<GameResponse> findGamesByLeagueApiId(Long leagueApiId) {
		return gameRepository.findAllByLeagueApiId(leagueApiId)
			.stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<GameResponse> findGamesByLeagueApiIdAndDay(Long leagueApiId, String date) {
		LocalDate dateTime = LocalDate.parse(date, formatter);
		LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(),
			0, 0);
		LocalDateTime end = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 23, 59);
		return gameRepository.findAllByLeagueApiIdAndKickoffBetween(leagueApiId, start, end)
			.stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
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

	public void updateGameLive(LocalDateTime localDateTime) {
		LocalDateTime kickoff = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
			localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute());
		List<Game> gameList = gameRepository.findAllByKickoff(kickoff);
		gameList.stream().forEach(game -> game.updateLive());
	}

	@Transactional(readOnly = true)
	public List<GameResponse> findGamesByStatus() {
		return gameRepository.findAllByStatus(GameStatus.LIVE)
			.stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<GameResponse> findGamesByLeagueApiIdAndStatus(Long leagueApiId) {
		return gameRepository.findAllByLeagueApiIdAndStatus(leagueApiId, GameStatus.LIVE)
			.stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<GameResponse> findGamesByDay(String date) {
		LocalDate dateTime = LocalDate.parse(date, formatter);
		LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(),
			0, 0);
		LocalDateTime end = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 23, 59);
		return gameRepository.findAllByKickoffBetween(start, end, Sort.by(Sort.Direction.ASC, "kickoff"))
			.stream()
			.map(GameResponse::from)
			.collect(Collectors.toList());
	}

	public void updateLiveGames() {
		List<Game> gameList = gameRepository.findAllByStatus(GameStatus.LIVE);
		gameList.stream()
			.forEach(game -> {
				try {
					update(game.getId(), game.getApiId().toString());
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			});
	}

	private String apiResponseToJson(MultiValueMap<String, String> params) {
		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
			.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
			.build();
		WebClient webClient = WebClient.builder()
			.exchangeStrategies(exchangeStrategies)
			.baseUrl("https://v3.football.api-sports.io")
			.defaultHeaders(httpHeaders -> {
				httpHeaders.add("x-rapidapi-key", apiKey);
				httpHeaders.add("x-rapidapi-host", "v3.football.api-sports.io");
			})
			.build();
		return webClient
			.get()
			.uri(uriBuilder -> uriBuilder.path("/fixtures").queryParams(params).build())
			.exchange()
			.block()
			.bodyToMono(String.class)
			.block();
	}
}

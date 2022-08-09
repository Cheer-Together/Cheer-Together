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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
	private final GameService gameService;

	@Value("${spring.api.secretKey}")
	private String apiKey;

	@GetMapping
	public ResponseEntity<List<GameResponse>> findGames() {
		return new ResponseEntity<>(gameService.findGames(), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> modify(@PathVariable Long id, @RequestParam String apiId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("id", apiId);
		try {
			gameService.update(id, apiResponseToJson(params));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<>(MODIFY_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> insert(@RequestBody GameApiParameterRequest gameApiParameterRequest) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("league", gameApiParameterRequest.getLeagueApiId());
		params.add("season", gameApiParameterRequest.getSeason());
		params.add("timezone", "Asia/Seoul");
		try {
			gameService.insert(apiResponseToJson(params));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<>(REGIST_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
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

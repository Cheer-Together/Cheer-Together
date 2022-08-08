package com.ssafy.cheertogether.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.cheertogether.member.JwtTokenProvider;
import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.service.Oauth2Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

	private final Oauth2Service oauth2Service;
	private final JwtTokenProvider jwtTokenProvider;

	//TODO: Response Dto 반환으로 변경
	@GetMapping("/kakao")
	public ResponseEntity<?> kakaoLogin(@RequestParam String code) {
		JsonElement element = JsonParser.parseString(code);
		code = element.getAsJsonObject().get("code").getAsString();

		String access_token = oauth2Service.getKakaoAccessToken(code);
		Member member = oauth2Service.createKakaoUser(access_token);
		return new ResponseEntity<>(jwtTokenProvider.createToken(String.valueOf(member.getId()))
			, HttpStatus.OK);
	}
}

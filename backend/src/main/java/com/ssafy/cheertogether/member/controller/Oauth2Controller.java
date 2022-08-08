package com.ssafy.cheertogether.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.cheertogether.member.JwtTokenProvider;
import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.dto.Oauth2Response;
import com.ssafy.cheertogether.member.service.Oauth2Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

	private final Oauth2Service oauth2Service;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/kakao")
	public ResponseEntity<Oauth2Response> kakaoCallBack(@RequestBody String code) {

		JsonElement element = JsonParser.parseString(code);
		code = element.getAsJsonObject().get("code").getAsString();

		String access_token = oauth2Service.getKakaoAccessToken(code);
		String email = oauth2Service.getEmail(access_token);
		boolean exist = oauth2Service.isExistEmail(email);

		if (exist) {  //바로 로그인
			Member member = oauth2Service.findMemberByEmail(email);
			return new ResponseEntity<>(
				new Oauth2Response(false, jwtTokenProvider.createToken(String.valueOf(member.getId())), email),
				HttpStatus.OK);
		} else {  //회원가입 페이지로 이동
			return new ResponseEntity<>(new Oauth2Response(true, null, email), HttpStatus.OK);
		}
	}
}

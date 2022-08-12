package com.ssafy.cheertogether.member.controller;

import static com.ssafy.cheertogether.member.MemberConstant.*;

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
import com.ssafy.cheertogether.member.dto.MemberJoinRequest;
import com.ssafy.cheertogether.member.dto.Oauth2JoinRequest;
import com.ssafy.cheertogether.member.dto.Oauth2Response;
import com.ssafy.cheertogether.member.service.MemberService;
import com.ssafy.cheertogether.member.service.Oauth2Service;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

	private final Oauth2Service oauth2Service;
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/kakao")
	@ApiOperation(value = "카카오 로그인", notes = "카카오로 처음 가입한 사용지인지 구분하여 동작")
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

	@PostMapping("/kakao/join")
	@ApiOperation(value = "카카오 회원가입 및 로그인", notes = "카카오 새로운 유저 회원가입/로그인")
	public ResponseEntity<String> kakaoJoinAndLogin(@RequestBody Oauth2JoinRequest oauth2JoinRequest) {
		oauth2Service.join(oauth2JoinRequest);
		Long memberId = memberService.login(oauth2JoinRequest.getEmail(), null);
		return new ResponseEntity<>(jwtTokenProvider.createToken(String.valueOf(memberId)), HttpStatus.OK);
	}
}

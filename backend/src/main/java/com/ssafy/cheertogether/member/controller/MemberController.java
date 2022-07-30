package com.ssafy.cheertogether.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.auth.JwtTokenProvider;
import com.ssafy.cheertogether.member.dto.LoginRequest;
import com.ssafy.cheertogether.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("asdfsf!", HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		log.info("email = " + loginRequest.getEmail() + " password = " + loginRequest.getPassword());
		memberService.login(loginRequest.getEmail(), loginRequest.getPassword());
		return new ResponseEntity<>(jwtTokenProvider.createToken(loginRequest.getEmail()), HttpStatus.OK);
	}
}

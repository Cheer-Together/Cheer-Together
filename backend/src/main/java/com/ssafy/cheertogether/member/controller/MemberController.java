package com.ssafy.cheertogether.member.controller;

import static com.ssafy.cheertogether.member.MemberConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.auth.JwtTokenProvider;
import com.ssafy.cheertogether.member.dto.MemberJoinRequest;
import com.ssafy.cheertogether.member.dto.MemberLoginRequest;
import com.ssafy.cheertogether.member.service.EmailService;
import com.ssafy.cheertogether.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final EmailService emailService;
	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping("/validate/duplicated")
	public ResponseEntity<String> checkDuplicateEmail(@RequestParam String email) {
		memberService.checkDuplicateEmail(email);
		return new ResponseEntity<>(CREATABLE_EMAIL_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@GetMapping("/authenticate/email")
	public ResponseEntity<String> sendEmail(@RequestParam String email) {
		return new ResponseEntity<>(emailService.sendMail(email), HttpStatus.OK);
	}

	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody MemberJoinRequest memberJoinRequest) {
		memberService.join(memberJoinRequest);
		return new ResponseEntity<>(JOIN_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody MemberLoginRequest memberLoginRequest) {
		log.info("email = " + memberLoginRequest.getEmail() + " password = " + memberLoginRequest.getPassword());
		memberService.login(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());
		return new ResponseEntity<>(jwtTokenProvider.createToken(memberLoginRequest.getEmail()), HttpStatus.OK);
	}
}

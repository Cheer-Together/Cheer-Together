package com.ssafy.cheertogether.member.controller;

import static com.ssafy.cheertogether.member.MemberConstant.*;

import com.ssafy.cheertogether.member.domain.Token;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.member.dto.MemberJoinRequest;
import com.ssafy.cheertogether.member.dto.MemberLoginRequest;
import com.ssafy.cheertogether.member.dto.MemberModifyPasswordRequest;
import com.ssafy.cheertogether.member.dto.MemberModifyRequest;
import com.ssafy.cheertogether.member.dto.MemberPointRankingResponse;
import com.ssafy.cheertogether.member.dto.MemberResponse;
import com.ssafy.cheertogether.member.service.EmailService;
import com.ssafy.cheertogether.member.service.MemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final EmailService emailService;

	@GetMapping("/{id}")
	@ApiOperation(value = "회원 조회", notes = "회원 단건 조회")
	public ResponseEntity<MemberResponse> search(@PathVariable Long id) {
		return new ResponseEntity<>(memberService.findMember(id), HttpStatus.OK);
	}

	@GetMapping("/validate/duplicated")
	@ApiOperation(value = "이메일 중복 확인", notes = "회원가입 시 이메일 중복 확인")
	public ResponseEntity<String> checkDuplicateEmail(
		@ApiParam(value = "이메일", required = true, example = "choijoohee@naver.com") @RequestParam String email) {
		memberService.checkDuplicateEmail(email);
		return new ResponseEntity<>(CREATABLE_EMAIL_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@GetMapping("/authenticate/email")
	@ApiOperation(value = "이메일 인증", notes = "이메일 인증을 위한 인증코드 메일 전송")
	public ResponseEntity<String> authenticateEmail(
		@ApiParam(value = "이메일", required = true, example = "choijoohee@naver.com") @RequestParam String email) {
		return new ResponseEntity<>(emailService.sendAuthenticationMail(email), HttpStatus.OK);
	}

	@PostMapping("/join")
	@ApiOperation(value = "회원가입", notes = "회원 가입 - 회원 등록")
	public ResponseEntity<String> join(@RequestBody MemberJoinRequest memberJoinRequest) {
		memberService.join(memberJoinRequest);
		return new ResponseEntity<>(JOIN_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "로그인")
	public ResponseEntity<String> login(@RequestBody MemberLoginRequest memberLoginRequest, HttpServletResponse response) {
		log.info("email = " + memberLoginRequest.getEmail() + " password = " + memberLoginRequest.getPassword());
		Token token = memberService.login(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());
		setRefreshTokenCookie(response, token);
		return new ResponseEntity<>(token.getAccessToken(), HttpStatus.OK);
	}

	private void setRefreshTokenCookie(HttpServletResponse response, Token token) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", token.getRefreshToken());
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(60 * 60 * 24 * 14); //2주
		response.addCookie(refreshTokenCookie);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "회원정보 수정", notes = "마이페이지에서 회원정보를 수정")
	public ResponseEntity<String> modify(@PathVariable Long id, @RequestBody MemberModifyRequest memberModifyRequest) {
		memberService.update(id, memberModifyRequest);
		return new ResponseEntity<>(MODIFY_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@PostMapping("{id}/password")
	@ApiOperation(value = "비밀번호 변경", notes = "마이페이지에서 비밀번호를 변경")
	public ResponseEntity<String> modifyPassword(@PathVariable Long id,
		@RequestBody MemberModifyPasswordRequest request) {
		memberService.confirmPassword(id, request);
		memberService.updatePassword(id, request.getNewPassword());
		return new ResponseEntity<>(MODIFY_PASSWORD_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "회원 탈퇴", notes = "마이페이지에서 회원 탈퇴")
	public ResponseEntity<String> withdraw(@PathVariable Long id) {
		memberService.delete(id);
		return new ResponseEntity<>(WITHDRAW_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@GetMapping("/find/password")
	@ApiOperation(value = "비밀번호 찾기", notes = "등록한 이메일로 임시 비밀번호를 전송")
	public ResponseEntity<String> findPassword(
		@ApiParam(value = "이메일", required = true, example = "choijoohee@naver.com") @RequestParam String email) {
		String tempPassword = memberService.findPassword(email);
		emailService.sendTempPassword(email, tempPassword);
		return new ResponseEntity<>(FIND_PASSWORD_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@PutMapping("/{id}/point/plus")
	@ApiOperation(value = "축구공(포인트) 증가", notes = "일정 축구공(포인트)을 증가시킴")
	public ResponseEntity<String> plusPoint(@PathVariable Long id,
		@ApiParam(value = "증가시킬 축구공(포인트) 수", required = true, example = "10") @RequestBody Map<String, Integer> point) {
		memberService.plusPoint(id, point.get("point"));
		return new ResponseEntity<>(String.format(PLUS_POINT_SUCCESS_RESPONSE_MESSAGE, point.get("point")),
			HttpStatus.OK);
	}

	@PutMapping("/{id}/point/subtract")
	@ApiOperation(value = "축구공(포인트) 감소", notes = "일정 축구공(포인트)을 감소시킴")
	public ResponseEntity<String> subtractPoint(@PathVariable Long id,
		@ApiParam(value = "감소시킬 축구공(포인트) 수", required = true, example = "10") @RequestBody Map<String, Integer> point) {
		memberService.subtractPoint(id, point.get("point"));
		return new ResponseEntity<>(String.format(SUBTRACT_POINT_SUCCESS_RESPONSE_MESSAGE, point.get("point")),
			HttpStatus.OK);
	}

	@GetMapping("/point/ranking")
	@ApiOperation(value = "축구공(포인트) 랭킹 가져오기", notes = "축구공(포인트) 랭킹 top3 회원을 가져옴")
	public ResponseEntity<List<MemberPointRankingResponse>> getPointRanking() {
		return new ResponseEntity<>(memberService.getPointRanking(), HttpStatus.OK);
	}
}

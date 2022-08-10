package com.ssafy.cheertogether.member.controller;

import static com.ssafy.cheertogether.member.MemberConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.cheertogether.common.FirebaseService;
import com.ssafy.cheertogether.member.JwtTokenProvider;
import com.ssafy.cheertogether.member.dto.MemberJoinRequest;
import com.ssafy.cheertogether.member.dto.MemberLoginRequest;
import com.ssafy.cheertogether.member.dto.MemberModifyPasswordRequest;
import com.ssafy.cheertogether.member.dto.MemberModifyRequest;
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
	private final FirebaseService firebaseService;
	private final JwtTokenProvider jwtTokenProvider;

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

	@PostMapping(value = "/join", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@ApiOperation(value = "회원가입", notes = "회원 가입 - 회원 등록")
	public ResponseEntity<String> join(@RequestParam MemberJoinRequest memberJoinRequest, @RequestParam MultipartFile file) {
		memberJoinRequest.saveProfileImgLink(firebaseService.uploadFiles(file));
		memberService.join(memberJoinRequest);
		return new ResponseEntity<>(JOIN_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "로그인")
	public ResponseEntity<String> login(@RequestBody MemberLoginRequest memberLoginRequest) {
		log.info("email = " + memberLoginRequest.getEmail() + " password = " + memberLoginRequest.getPassword());
		Long id = memberService.login(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());
		return new ResponseEntity<>(jwtTokenProvider.createToken(String.valueOf(id)), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "회원정보 수정", notes = "마이페이지에서 회원정보를 수정")
	public ResponseEntity<String> modify(@PathVariable Long id, @RequestBody MemberModifyRequest memberModifyRequest) {
		//memberModifyRequest.saveProfileImgLink(firebaseService.uploadFiles(file));
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

}

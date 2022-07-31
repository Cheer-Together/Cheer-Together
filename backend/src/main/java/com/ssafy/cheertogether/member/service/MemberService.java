package com.ssafy.cheertogether.member.service;

import static com.ssafy.cheertogether.member.MemberConstant.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.dto.MemberJoinRequest;
import com.ssafy.cheertogether.member.exception.DuplicatedEmailException;
import com.ssafy.cheertogether.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;

	/**
	 * 회원 가입
	 * @param memberJoinRequest 회원가입 요청 폼
	 */
	public void join(MemberJoinRequest memberJoinRequest) {
		memberRepository.save(Member.from(memberJoinRequest));
	}

	/**
	 * JWT 토큰을 이용한 로그인
	 * @param email 사용자 이메일
	 * @param password 사용자 패스워드
	 *
	 */
	@Transactional(readOnly = true)
	public void login(String email, String password) {
		Member findMember = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException(MISMATCH_EMAIL_ERROR_MESSAGE));
		if (!findMember.confirmPassword(password)) {
			throw new IllegalArgumentException(MISMATCH_PASSWORD_ERROR_MESSAGE);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException(EMAIL_NOT_FOUND_ERROR_MESSAGE));
	}

	@Transactional(readOnly = true)
	public void checkDuplicateEmail(String email) {
		boolean isDuplicated = memberRepository.findByEmail(email).isPresent();
		if (isDuplicated) {
			throw new DuplicatedEmailException();
		}
	}
}

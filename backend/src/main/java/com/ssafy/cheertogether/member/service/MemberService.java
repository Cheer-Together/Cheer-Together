package com.ssafy.cheertogether.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;

	/**
	 * JWT 토큰을 이용한 로그인
	 * @param email 사용자 이메일
	 * @param password 사용자 패스워드
	 * @return JWT 토큰 값
	 */
	@Transactional(readOnly = true)
	public void login(String email, String password) {
		Member findMember = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));
		if (!findMember.confirmPassword(password)) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("아이디/비밀번호가 틀립니다."));
	}
}

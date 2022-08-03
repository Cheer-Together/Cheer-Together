package com.ssafy.cheertogether.member.service;

import static com.ssafy.cheertogether.member.MemberConstant.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.favorite.domain.FavoriteLeague;
import com.ssafy.cheertogether.favorite.repository.FavoriteLeagueRepository;
import com.ssafy.cheertogether.league.repository.LeagueRepository;
import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.dto.MemberJoinRequest;
import com.ssafy.cheertogether.member.dto.MemberModifyRequest;
import com.ssafy.cheertogether.member.exception.DuplicatedEmailException;
import com.ssafy.cheertogether.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final LeagueRepository leagueRepository;
	private final FavoriteLeagueRepository favoriteLeagueRepository;

	/**
	 * 회원 가입
	 * @param memberJoinRequest 회원가입 요청 폼
	 */
	public void join(MemberJoinRequest memberJoinRequest) {
		Member member = Member.from(memberJoinRequest);
		List<FavoriteLeague> favoriteLeagueList = new ArrayList<>();
		for (Integer leagueApiId : memberJoinRequest.getFavoriteLeagueList()) {
			favoriteLeagueList.add(FavoriteLeague.from(member, leagueRepository.findLeagueByApiId(leagueApiId).get()));
		}
		member.setFavoriteLeagueList(favoriteLeagueList);
		memberRepository.save(member);
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

	public void update(Long id, MemberModifyRequest memberModifyRequest) {
		Member findMember = memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MEMBER_ERROR_MESSAGE));
		findMember.update(memberModifyRequest);
		favoriteLeagueRepository.deleteFavoriteLeagueByMember_Email(findMember.getEmail());
		List<FavoriteLeague> favoriteLeagueList = new ArrayList<>();
		for (Integer leagueApiId : memberModifyRequest.getFavoriteLeagueList()) {
			favoriteLeagueList.add(
				FavoriteLeague.from(findMember, leagueRepository.findLeagueByApiId(leagueApiId).get()));
		}
		findMember.setFavoriteLeagueList(favoriteLeagueList);

	}

	public void delete(Long id) {
		memberRepository.deleteById(id);
	}

	public String findPassword(String email) {
		Member findMember = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException(MISMATCH_EMAIL_ERROR_MESSAGE));
		String tempPassword = getTempPassword(20);
		findMember.setTempPassword(tempPassword);
		return tempPassword;
	}

	private String getTempPassword(int size) {
		char[] charSet = new char[] {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z',
			'!', '@', '#', '$', '%', '^', '&'};
		StringBuilder sb = new StringBuilder();
		SecureRandom sr = new SecureRandom();
		sr.setSeed(new Date().getTime());

		int idx = 0;
		int len = charSet.length;
		for (int i = 0; i < size; i++) {
			idx = sr.nextInt(len);
			sb.append(charSet[idx]);
		}
		return sb.toString();
	}
}

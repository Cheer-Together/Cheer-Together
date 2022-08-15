package com.ssafy.cheertogether.member.service;

import static com.ssafy.cheertogether.member.MemberConstant.*;
import static com.ssafy.cheertogether.league.LeagueConstant.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.favorite.domain.FavoriteLeague;
import com.ssafy.cheertogether.favorite.domain.FavoriteTeam;
import com.ssafy.cheertogether.favorite.repository.FavoriteLeagueRepository;
import com.ssafy.cheertogether.favorite.repository.FavoriteTeamRepository;
import com.ssafy.cheertogether.league.repository.LeagueRepository;
import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.dto.MemberJoinRequest;
import com.ssafy.cheertogether.member.dto.MemberModifyPasswordRequest;
import com.ssafy.cheertogether.member.dto.MemberModifyRequest;
import com.ssafy.cheertogether.member.dto.MemberPointRankingResponse;
import com.ssafy.cheertogether.member.dto.MemberResponse;
import com.ssafy.cheertogether.member.exception.DuplicatedEmailException;
import com.ssafy.cheertogether.member.repository.MemberRepository;
import com.ssafy.cheertogether.team.TeamConstant;
import com.ssafy.cheertogether.team.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final LeagueRepository leagueRepository;
	private final FavoriteLeagueRepository favoriteLeagueRepository;
	private final TeamRepository teamRepository;
	private final FavoriteTeamRepository favoriteTeamRepository;

	@Transactional(readOnly = true)
	public MemberResponse findMember(Long id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MEMBER_ERROR_MESSAGE));
		return new MemberResponse(member);
	}

	/**
	 * 회원 가입
	 * @param memberJoinRequest 회원가입 요청 폼
	 */
	public void join(MemberJoinRequest memberJoinRequest) {
		Member member = Member.from(memberJoinRequest);
		List<FavoriteLeague> favoriteLeagueList = new ArrayList<>();
		if (!memberJoinRequest.getFavoriteLeagueList().isEmpty()) {
			favoriteLeagueList.addAll(memberJoinRequest.getFavoriteLeagueList()
				.stream()
				.map(leagueApiId -> FavoriteLeague.from(member, leagueRepository.findLeagueByApiId(leagueApiId)
					.orElseThrow(() -> new IllegalArgumentException(MISMATCH_APIID_ERROR_MESSAGE))))
				.collect(Collectors.toList()));
		}
		member.setFavoriteLeagueList(favoriteLeagueList);
		List<FavoriteTeam> favoriteTeamList = new ArrayList<>();
		if (!memberJoinRequest.getFavoriteTeamList().isEmpty()) {
			favoriteTeamList.addAll(memberJoinRequest.getFavoriteTeamList()
				.stream()
				.map(teamApiId -> FavoriteTeam.from(member, teamRepository.findTeamByApiId(teamApiId)
					.orElseThrow(() -> new IllegalArgumentException(TeamConstant.MISMATCH_APIID_ERROR_MESSAGE))))
				.collect(Collectors.toList()));
		}
		member.setFavoriteTeamList(favoriteTeamList);
		memberRepository.save(member);
	}

	/**
	 * JWT 토큰을 이용한 로그인
	 * @param email 사용자 이메일
	 * @param password 사용자 패스워드
	 *
	 */
	public Long login(String email, String password) {
		Member findMember = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException(MISMATCH_EMAIL_ERROR_MESSAGE));
		if (!findMember.confirmPassword(password)) {
			throw new IllegalArgumentException(MISMATCH_PASSWORD_ERROR_MESSAGE);
		}
		findMember.checkAttendance();
		return findMember.getId();
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		return memberRepository.findById(Long.parseLong(id))
			.orElseThrow(() -> new UsernameNotFoundException(NOT_FOUND_MEMBER_ERROR_MESSAGE));
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
		favoriteLeagueRepository.deleteFavoriteLeagueByMember_Id(findMember.getId());
		List<FavoriteLeague> favoriteLeagueList = new ArrayList<>();
		if (!memberModifyRequest.getFavoriteLeagueList().isEmpty()) {
			favoriteLeagueList.addAll(memberModifyRequest.getFavoriteLeagueList()
				.stream()
				.map(leagueApiId -> FavoriteLeague.from(findMember, leagueRepository.findLeagueByApiId(leagueApiId)
					.orElseThrow(() -> new IllegalArgumentException(MISMATCH_APIID_ERROR_MESSAGE))))
				.collect(Collectors.toList()));
		}
		findMember.setFavoriteLeagueList(favoriteLeagueList);
		favoriteTeamRepository.deleteFavoriteTeamByMember_Id(findMember.getId());
		List<FavoriteTeam> favoriteTeamList = new ArrayList<>();
		if (!memberModifyRequest.getFavoriteTeamList().isEmpty()) {
			favoriteTeamList.addAll(memberModifyRequest.getFavoriteTeamList()
				.stream()
				.map(teamApiId -> FavoriteTeam.from(findMember, teamRepository.findTeamByApiId(teamApiId)
					.orElseThrow(() -> new IllegalArgumentException(TeamConstant.MISMATCH_APIID_ERROR_MESSAGE))))
				.collect(Collectors.toList()));
		}
		findMember.setFavoriteTeamList(favoriteTeamList);
	}

	public void updatePassword(Long id, String newPassword) {
		Member findMember = memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MEMBER_ERROR_MESSAGE));
		findMember.updatePassword(newPassword);
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
		char[] charSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
			'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&'};
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

	@Transactional(readOnly = true)
	public void confirmPassword(Long id, MemberModifyPasswordRequest request) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MEMBER_ERROR_MESSAGE));
		if (!member.confirmPassword(request.getCurPassword())) {
			throw new IllegalArgumentException(MISMATCH_PASSWORD_ERROR_MESSAGE);
		}
	}

	public void plusPoint(Long id, int point) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MEMBER_ERROR_MESSAGE));
		member.plusPoint(point);
	}

	public void subtractPoint(Long id, int point) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MEMBER_ERROR_MESSAGE));
		member.subtractPoint(point);
	}

	public List<MemberPointRankingResponse> getPointRanking() {
		return memberRepository.findTop4ByOrderByPointDesc().stream()
			.map(MemberPointRankingResponse::new)
			.collect(Collectors.toList());
	}
}

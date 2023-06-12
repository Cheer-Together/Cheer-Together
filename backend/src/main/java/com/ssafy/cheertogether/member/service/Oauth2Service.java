package com.ssafy.cheertogether.member.service;

import static com.ssafy.cheertogether.league.LeagueConstant.*;

import com.ssafy.cheertogether.member.JwtTokenProvider;
import com.ssafy.cheertogether.member.domain.RefreshToken;
import com.ssafy.cheertogether.member.domain.Token;
import com.ssafy.cheertogether.member.repository.RefreshTokenRepository;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.cheertogether.favorite.domain.FavoriteLeague;
import com.ssafy.cheertogether.favorite.domain.FavoriteTeam;
import com.ssafy.cheertogether.league.repository.LeagueRepository;
import com.ssafy.cheertogether.member.MemberConstant;
import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.dto.Oauth2JoinRequest;
import com.ssafy.cheertogether.member.repository.MemberRepository;
import com.ssafy.cheertogether.team.TeamConstant;
import com.ssafy.cheertogether.team.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class Oauth2Service {

	@Value("${oauth2.kakao.restApiKey}")
	private String kakao_restApiKey;

	@Value("${oauth2.kakao.redirectUri}")
	private String kakao_redirectUri;

	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final LeagueRepository leagueRepository;
	private final TeamRepository teamRepository;

	private final JwtTokenProvider jwtTokenProvider;


	public String getKakaoAccessToken(String code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			//POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			//POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			String sb = "grant_type=authorization_code" + "&client_id=" + kakao_restApiKey + "&redirect_uri="
				+ kakao_redirectUri // TODO 인가코드 받은 redirect_uri 입력
				+ "&code=" + code;
			bw.write(sb);
			bw.flush();

			//결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			StringBuilder result = new StringBuilder();

			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			System.out.println("response body : " + result);

			//Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonElement element = JsonParser.parseString(result.toString());

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return access_Token;
	}

	public String getEmail(String token) {
		String reqURL = "https://kapi.kakao.com/v2/user/me";

		//access_token을 이용하여 사용자 정보 조회
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

			//결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			StringBuilder result = new StringBuilder();

			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			br.close();
			System.out.println("response body : " + result);

			//Gson 라이브러리로 JSON파싱
			JsonElement element = JsonParser.parseString(result.toString());
			return element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
			// Optional<Member> member = memberRepository.findByEmail(email);
			// return member.orElseGet(() ->  {
			// 	Member newMember = new Member(email, nickname, "", "", "user", "하이");
			// 	memberRepository.save(newMember);
			// 	return newMember;
			// });
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isExistEmail(String email) {
		return memberRepository.findByEmail(email).isPresent();
	}

	public Token login(String email) {
		Member findMember = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException(MemberConstant.MISMATCH_EMAIL_ERROR_MESSAGE));
		Token token = jwtTokenProvider.createTokens(String.valueOf(findMember.getId()));
		saveRefreshToken(token.getRefreshToken(), findMember.getId());
		return token;
	}

	private void saveRefreshToken(String token, Long userId) {
		RefreshToken refreshToken = RefreshToken.builder()
				.token(token)
				.userId(userId)
				.build();
		refreshTokenRepository.save(refreshToken);
	}

	public void join(Oauth2JoinRequest oauth2JoinRequest) {
		Member member = Member.from(oauth2JoinRequest);
		List<FavoriteLeague> favoriteLeagueList = new ArrayList<>();
		if (!oauth2JoinRequest.getFavoriteLeagueList().isEmpty()) {
			favoriteLeagueList.addAll(oauth2JoinRequest.getFavoriteLeagueList()
				.stream()
				.map(leagueApiId -> FavoriteLeague.from(member, leagueRepository.findLeagueByApiId(leagueApiId)
					.orElseThrow(() -> new IllegalArgumentException(MISMATCH_APIID_ERROR_MESSAGE))))
				.collect(Collectors.toList()));
		}
		member.setFavoriteLeagueList(favoriteLeagueList);
		List<FavoriteTeam> favoriteTeamList = new ArrayList<>();
		if (!oauth2JoinRequest.getFavoriteTeamList().isEmpty()) {
			favoriteTeamList.addAll(oauth2JoinRequest.getFavoriteTeamList()
				.stream()
				.map(teamApiId -> FavoriteTeam.from(member, teamRepository.findTeamByApiId(teamApiId)
					.orElseThrow(() -> new IllegalArgumentException(TeamConstant.MISMATCH_APIID_ERROR_MESSAGE))))
				.collect(Collectors.toList()));
		}
		member.setFavoriteTeamList(favoriteTeamList);
		memberRepository.save(member);
	}
}

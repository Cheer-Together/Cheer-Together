package com.ssafy.cheertogether.member;

import com.ssafy.cheertogether.member.domain.Token;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ssafy.cheertogether.member.service.MemberService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

/**
 * 토큰을 생성하고 검증하는 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	@Value("${spring.jwt.secretKey}")
	private String secretKey;
	private final MemberService memberService;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	/**
	 * JWT 토큰 생성 메서드
	 * @param payload 사용자 pk 값
	 * @return JWT 토큰
	 */
	public Token createTokens(String payload) {
		String accessToken = createAccessToken(payload);
		String refreshToken = createRefreshToken(payload);
		return new Token(accessToken, refreshToken);

	}

	private String createAccessToken(String payload) {
		long accessTokenValidTime = 60 * 60 * 1000L;  // 토큰 유효시간 1시간
		return createJwtToken(payload, accessTokenValidTime);
	}

	private String createRefreshToken(String payload) {
		long refreshTokenValidTime = 14 * 24 * 60 * 1000L;  // 토큰 유효시간 2주
		return createJwtToken(payload, refreshTokenValidTime);
	}

	private String createJwtToken(String payload, long tokenValidTime) {
		Claims claims = Jwts.claims().setSubject(payload);
		Date now = new Date();

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + tokenValidTime))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	/**
	 * JWT 토큰에서 인증 정보 조회
	 * @param token JWT 토큰
	 * @return 인증 정보
	 */
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = memberService.loadUserByUsername(getMemberId(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	/**
	 * 토큰에서 회원 정보 추출
	 * @param token JWT 토큰
	 * @return 회원 정보
	 */
	public String getMemberId(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * request의 헤더에서 토큰값을 가져온다.
	 * @param req 서블릿 리퀘스트
	 * @return 헤더의 토큰값
	 */
	public String resolveToken(HttpServletRequest req) {
		return req.getHeader("Authorization");
	}

	/**
	 * 토큰의 유효성 + 만료일자 확인
	 * @param jwtToken JWT 토큰
	 * @return 유효성 여부
	 */
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

}

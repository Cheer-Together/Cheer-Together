package com.ssafy.cheertogether.member.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.cheertogether.member.domain.Token;
import com.ssafy.cheertogether.member.dto.Oauth2JoinRequest;
import com.ssafy.cheertogether.member.dto.Oauth2Response;
import com.ssafy.cheertogether.member.service.Oauth2Service;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final Oauth2Service oauth2Service;

    @PostMapping("/kakao")
    @ApiOperation(value = "카카오 로그인", notes = "카카오로 처음 가입한 사용지인지 구분하여 동작")
    public ResponseEntity<Oauth2Response> kakaoCallBack(@RequestBody String code, HttpServletResponse response) {

        JsonElement element = JsonParser.parseString(code);
        code = element.getAsJsonObject().get("code").getAsString();

        String kakaoAccessToken = oauth2Service.getKakaoAccessToken(code);
        String email = oauth2Service.getEmail(kakaoAccessToken);
        boolean exist = oauth2Service.isExistEmail(email);

        if (exist) {  //바로 로그인
            String accessToken = kakaoLogin(response, email);
            return new ResponseEntity<>(
                    new Oauth2Response(false, accessToken, email),
                    HttpStatus.OK);
        } else {  //회원가입 페이지로 이동
            return new ResponseEntity<>(new Oauth2Response(true, null, email), HttpStatus.OK);
        }
    }

    @PostMapping("/kakao/join")
    @ApiOperation(value = "카카오 회원가입 및 로그인", notes = "카카오 새로운 유저 회원가입/로그인")
    public ResponseEntity<String> kakaoJoinAndLogin(@RequestBody Oauth2JoinRequest oauth2JoinRequest,
                                                    HttpServletResponse response) {
        oauth2Service.join(oauth2JoinRequest);
		String accessToken = kakaoLogin(response, oauth2JoinRequest.getEmail());
		return new ResponseEntity<>(accessToken, HttpStatus.OK);
	}

	private String kakaoLogin(HttpServletResponse response, String email) {
		Token token = oauth2Service.login(email);
		setRefreshTokenCookie(response, token.getRefreshToken());
		return token.getAccessToken();
	}

	private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(60 * 60 * 24 * 14); //2주
		response.addCookie(refreshTokenCookie);
	}
}

package com.ssafy.cheertogether.member.service;

import static com.ssafy.cheertogether.member.MemberConstant.*;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {
	private final JavaMailSender javaMailSender;

	/**
	 * 이메일 인증을 받고자하는 사용자 이메일에 인증코드를 보낸다.
	 * @param toEmail 인증코드를 받을 사용자 이메일
	 * @return 이메일 인증코드
	 */
	public String sendMail(String toEmail) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

		try {
			helper.setFrom("happyhouseprj@naver.com");
			helper.setTo(toEmail);
			helper.setSubject("같이집관 회원가입 이메일 인증");
		} catch (MessagingException e) {
			throw new IllegalStateException(SEND_EMAIL_AUTHENTICATION_ERROR_MESSAGE);
		}
		String certifiedCode = getCertifiedCode();
		String content = setContent(certifiedCode);
		try {
			helper.setText(content, true);
		} catch (MessagingException e) {
			throw new IllegalStateException(SEND_EMAIL_AUTHENTICATION_ERROR_MESSAGE);
		}
		javaMailSender.send(mimeMessage);
		return certifiedCode;
	}

	private String setContent(String certifiedCode) {
		return "<!DOCTYPE html>" + "<html>" + "<head>" + "</head>" + "<body>" + " <div"
			+ "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px;"
			+ " border-top: 4px solid #1976d2; margin: 20px auto; padding: 30px 0; box-sizing: border-box;\">"
			+ "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"
			+ "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">같이집관</span><br />"
			+ "		<span style=\"color: #1976d2\">메일인증</span> 안내입니다." + "	</h1>\n"
			+ "<img width=300 src='https://cdn.discordapp.com/attachments/994479416413540422/1002104410060632064/-_-011.png'>"
			+ "	<p style=\"font-size: 17px; line-height: 26px; margin-top: 10px; padding: 0 5px;\">" + "안녕하세요.<br />"
			+ "같이집관에 관심을 주셔서 진심으로 감사드립니다.<br />" + "아래 인증코드를 입력하여 회원가입을 완료해주세요.<br />" + "		감사합니다." + "</p>"
			+ "<h2>" + certifiedCode + "</h2>" + "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"
			+ " </div>" + "</body>" + "</html>";
	}

	private String getCertifiedCode() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		do {
			int num = random.nextInt(75) + 48;
			if (num <= 57 || num >= 65 && num <= 90 || num >= 97) {
				sb.append((char)num);
			}
		} while (sb.length() < 10);
		return sb.toString();
	}
}

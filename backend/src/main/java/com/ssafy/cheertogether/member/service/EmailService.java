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
	 */
	public String sendAuthenticationMail(String toEmail) {
		String certifiedCode = getCertifiedCode();
		String content = setAuthenticateEmailContent(certifiedCode);
		sendMail(toEmail, "같이집관 회원가입 이메일 인증", content);
		return certifiedCode;
	}

	/**
	 * 임시 비밀번호를 사용자의 이메일로 전송
	 * @param toEmail 임시 비밀번호를 받을 사용자 이메일
	 * @param tempPassword 임시 비밀번호(영문,숫자,기호 포함 20자리 랜덤값)
	 */
	public void sendTempPassword(String toEmail, String tempPassword) {
		sendMail(toEmail, "같이집관 임시 비밀번호 발급", setTempPasswordEmailContent(tempPassword));
	}

	/**
	 * 이메일 전송
	 * @param toEmail 전송할 이메일
	 * @param subject 이메일 제목
	 * @param content 이메일 내용
	 */
	private void sendMail(String toEmail, String subject, String content) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

		try {
			helper.setFrom("happyhouseprj@naver.com");
			helper.setTo(toEmail);
			helper.setSubject(subject);
		} catch (MessagingException e) {
			throw new IllegalStateException(SEND_EMAIL_ERROR_MESSAGE);
		}
		try {
			helper.setText(content, true);
		} catch (MessagingException e) {
			throw new IllegalStateException(SEND_EMAIL_ERROR_MESSAGE);
		}
		javaMailSender.send(mimeMessage);
	}

	private String setAuthenticateEmailContent(String certifiedCode) {
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

	private String setTempPasswordEmailContent(String tempPassword) {
		return "<!DOCTYPE html>" + "<html>" + "<head>" + "</head>" + "<body>" + " <div"
			+ "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px;"
			+ " border-top: 4px solid #1976d2; margin: 20px auto; padding: 30px 0; box-sizing: border-box;\">"
			+ "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"
			+ "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">같이집관</span><br />"
			+ "		<span style=\"color: #1976d2\">임시 비밀번호</span> 입니다." + "	</h1>\n"
			+ "<img width=300 src='https://cdn.discordapp.com/attachments/994479416413540422/1002104410060632064/-_-011.png'>"
			+ "	<p style=\"font-size: 17px; line-height: 26px; margin-top: 10px; padding: 0 5px;\">" + "안녕하세요.<br />"
			+ "현재 회원님의 비밀번호를 아래 임시 비밀번호로 변경해두었습니다.<br /> 아래 임시 비밀번호를 입력하여 로그인을 완료한 후에, <br />"
			+ "비밀번호를 수정하시는 것을 권장드립니다.<br />		감사합니다.</p>" + "<h2>" + tempPassword + "</h2>"
			+ "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" + " </div>" + "</body>" + "</html>";
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

package com.ssafy.cheertogether.member.exception;

import static com.ssafy.cheertogether.member.MemberConstant.*;

public class DuplicatedEmailException extends RuntimeException {

	public DuplicatedEmailException() {
		super(DUPLICATED_EMAIL_ERROR_MESSAGE);
	}
}

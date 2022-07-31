package com.ssafy.cheertogether.common.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.cheertogether.member.exception.DuplicatedEmailException;

@RestControllerAdvice
public class ExceptionAdvice {
	@ExceptionHandler(DuplicatedEmailException.class)
	public ResponseEntity<String> duplicatedEmailException(DuplicatedEmailException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}

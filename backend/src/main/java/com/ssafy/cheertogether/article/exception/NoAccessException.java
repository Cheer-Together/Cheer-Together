package com.ssafy.cheertogether.article.exception;

import static com.ssafy.cheertogether.article.ArticleConstant.*;

public class NoAccessException extends RuntimeException {
	public NoAccessException() {
		super(NO_ACCESS_ERROR_MESSAGE);
	}
}

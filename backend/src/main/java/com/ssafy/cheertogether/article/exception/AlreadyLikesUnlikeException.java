package com.ssafy.cheertogether.article.exception;

import static com.ssafy.cheertogether.article.ArticleConstant.*;

public class AlreadyLikesUnlikeException extends RuntimeException{
	public AlreadyLikesUnlikeException() {super(ALREADY_LIKES_UNLIKE_ERROR_MESSAGE);};
}

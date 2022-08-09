package com.ssafy.cheertogether.article.dto;

import lombok.Getter;

@Getter
public class ArticleRegisterRequest {
	private Long leagueApiId;
	private String title;
	private String content;
}

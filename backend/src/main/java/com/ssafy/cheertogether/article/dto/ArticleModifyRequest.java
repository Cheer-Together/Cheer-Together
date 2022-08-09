package com.ssafy.cheertogether.article.dto;

import lombok.Getter;

@Getter
public class ArticleModifyRequest {
	private String title;
	private String content;
	private Long leagueApiId;
}

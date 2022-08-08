package com.ssafy.cheertogether.article.dto;

import java.sql.Timestamp;

import com.ssafy.cheertogether.article.domain.Reply;

import lombok.Getter;

@Getter
public class ReplyResponse {
	private Long id;
	private String content;
	private Long memberId;
	private Long articleId;
	private Timestamp createDate;

	public ReplyResponse(Reply reply) {
		id = reply.getId();
		content = reply.getContent();
		memberId = reply.getMember().getId();
		articleId = reply.getArticle().getId();
		createDate = reply.getCreateDate();
	}
}

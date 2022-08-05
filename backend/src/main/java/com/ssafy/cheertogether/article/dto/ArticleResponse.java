package com.ssafy.cheertogether.article.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.ssafy.cheertogether.article.domain.Article;
import com.ssafy.cheertogether.league.domain.League;
import com.ssafy.cheertogether.member.domain.Member;

import lombok.Getter;

@Getter
public class ArticleResponse {
	private Long id;
	private String memberNickName;
	private String memberEmail;
	private String leagueName;
	private int leagueApiId;
	private String title;
	private String content;
	private Timestamp createDate;
	private Long likes;
	private Long unlike;
	private Long hit;

	public ArticleResponse (Article article, Member member, League league) {
		this.id = article.getId();
		this.memberNickName = member.getNickname();
		this.memberEmail = member.getEmail();
		this.leagueName = league.getHanName();
		this.leagueApiId = league.getApiId();
		this.title = article.getTitle();
		this.content = article.getContent();
		this.createDate = article.getCreateDate();
		this.likes = article.getLikes();
		this.unlike = article.getUnlike();
		this.hit = article.getHit();
	}
}

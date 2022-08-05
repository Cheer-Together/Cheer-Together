package com.ssafy.cheertogether.article.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ssafy.cheertogether.article.domain.Article;
import com.ssafy.cheertogether.article.domain.Reply;
import com.ssafy.cheertogether.league.domain.League;
import com.ssafy.cheertogether.league.dto.LeagueResponse;
import com.ssafy.cheertogether.league.dto.LeagueResponseExceptTeamList;
import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.dto.MemberResponse;

import lombok.Getter;

@Getter
public class ArticleResponse {
	private Long id;
	private MemberResponse memberResponse;
	private LeagueResponseExceptTeamList leagueResponseExceptTeamList;
	private String title;
	private String content;
	private Timestamp createDate;
	private Long likes;
	private Long unlike;
	private Long hit;
	private List<Reply> replyList;

	public ArticleResponse (Article article) {
		this.id = article.getId();
		this.memberResponse = new MemberResponse(article.getMember());
		this.leagueResponseExceptTeamList = new LeagueResponseExceptTeamList(article.getLeague());
		this.title = article.getTitle();
		this.content = article.getContent();
		this.createDate = article.getCreateDate();
		this.likes = article.getLikes();
		this.unlike = article.getUnlike();
		this.hit = article.getHit();
		this.replyList = article.getReplyList();
	}
}

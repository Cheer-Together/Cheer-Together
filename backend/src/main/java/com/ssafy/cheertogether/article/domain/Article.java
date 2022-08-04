package com.ssafy.cheertogether.article.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

import com.ssafy.cheertogether.article.dto.ArticleRegisterRequest;
import com.ssafy.cheertogether.league.domain.League;
import com.ssafy.cheertogether.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "league_id")
	private League league;

	private String title;
	@Column(columnDefinition = "TEXT")
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@ColumnDefault("0")
	private Long likes;
	@ColumnDefault("0")
	private Long unlike;
	@ColumnDefault("0")
	private Long hit;

	@Builder
	private Article(Member member, League league, String title, String content, Long likes, Long unlike, Long hit) {
		this.member = member;
		this.league = league;
		this.title = title;
		this.content = content;
	}

	public static Article from(ArticleRegisterRequest articleRegisterRequest) {
		return Article.builder()
			.title(articleRegisterRequest.getTitle())
			.content(articleRegisterRequest.getContent())
			.build();
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setLeague(League league) {
		this.league = league;
	}
}

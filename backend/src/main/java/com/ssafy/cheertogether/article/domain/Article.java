package com.ssafy.cheertogether.article.domain;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.cheertogether.article.dto.ArticleModifyRequest;
import com.ssafy.cheertogether.article.dto.ArticleRegisterRequest;
import com.ssafy.cheertogether.league.domain.League;
import com.ssafy.cheertogether.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
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
	private Timestamp createDate;
	@ColumnDefault("0")
	private Long likes;
	@ColumnDefault("0")
	private Long unlike;
	@ColumnDefault("0")
	private Long hit;
	@OneToMany(mappedBy = "article")
	@JsonManagedReference
	private List<Reply> replyList = new ArrayList<>();

	@Builder
	private Article(Member member, League league, String title, String content) {
		this.member = member;
		this.league = league;
		this.title = title;
		this.content = content;
	}

	public static Article from(ArticleRegisterRequest articleRegisterRequest, Member member, League league) {
		return Article.builder()
			.member(member)
			.league(league)
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

	public void setCreateDate() {
		this.createDate = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant());
	}

	public void update(ArticleModifyRequest articleModifyRequest) {
		this.title = articleModifyRequest.getTitle();
		this.content = articleModifyRequest.getContent();
	}

	public void uphit() {
		this.hit++;
	}

	public void uplikes() {
		this.likes++;
	}

	public void unlike() {
		this.unlike++;
	}

	public void addReply(Reply reply) {
		this.replyList.add(reply);
		if (reply.getArticle() != this) {
			reply.setArticle(this);
		}
	}
}

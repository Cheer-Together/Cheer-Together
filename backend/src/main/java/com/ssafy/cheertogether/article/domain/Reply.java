package com.ssafy.cheertogether.article.domain;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.cheertogether.article.dto.ReplyRequest;
import com.ssafy.cheertogether.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	@JsonBackReference
	Article article;

	@Column(columnDefinition = "TEXT")
	private String content;
	private Timestamp createDate;

	@Builder
	public Reply(String content) {
		this.content = content;
	}

	public static Reply from(ReplyRequest replyRequest) {
		return Reply.builder()
			.content(replyRequest.getContent())
			.build();
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setArticle(Article article) {
		this.article = article;
		if(!article.getReplyList().contains(this)) {
			article.getReplyList().add(this);
		}
	}

	public void update(ReplyRequest replyRequest) {
		this.content = replyRequest.getContent();
	}

	public void setCreateDate() {
		this.createDate = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant());
	}
}

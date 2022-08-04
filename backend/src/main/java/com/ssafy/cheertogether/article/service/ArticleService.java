package com.ssafy.cheertogether.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.article.domain.Article;
import com.ssafy.cheertogether.article.dto.ArticleRegisterRequest;
import com.ssafy.cheertogether.article.dto.ArticleResponse;
import com.ssafy.cheertogether.article.repository.ArticleRepository;
import com.ssafy.cheertogether.league.repository.LeagueRepository;
import com.ssafy.cheertogether.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final MemberRepository memberRepository;
	private final LeagueRepository leagueRepository;

	public void regist(ArticleRegisterRequest articleRegisterRequest) {
		Article article = Article.from(articleRegisterRequest);
		article.setMember(memberRepository.getReferenceById(articleRegisterRequest.getMemberId()));
		article.setLeague(leagueRepository.findLeagueByApiId(articleRegisterRequest.getLeagueApiId()).get());
		articleRepository.save(article);
	}

	public List<ArticleResponse> findAll() {
		return articleRepository
			.findAll()
			.stream()
			.map(article -> new ArticleResponse(article, article.getMember(), article.getLeague()))
			.collect(Collectors.toList());
	}

	public List<ArticleResponse> findByHeader(int leagueApiId) {
		return articleRepository
			.findArticlesByLeague_ApiId(leagueApiId)
			.stream()
			.map(article -> new ArticleResponse(article, article.getMember(), article.getLeague()))
			.collect(Collectors.toList());
	}

	public List<ArticleResponse> findAllSearchByTitleContent(String keyword) {
		return articleRepository
			.findArticlesByTitleContainingOrContentContaining(keyword, keyword)
			.stream()
			.map(article -> new ArticleResponse(article, article.getMember(), article.getLeague()))
			.collect(Collectors.toList());
	}
}

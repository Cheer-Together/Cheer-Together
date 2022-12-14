package com.ssafy.cheertogether.article.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.article.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findArticlesByLeague_ApiId(Long leagueApiId, Sort sort);

	List<Article> findArticlesByTitleContainingOrContentContaining(String title, String content, Sort sort);
}

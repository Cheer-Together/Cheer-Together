package com.ssafy.cheertogether.article.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.article.domain.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long> {
	Optional<Likes> findLikesByArticle_IdAndMember_Id(Long articleId, Long memberId);
}

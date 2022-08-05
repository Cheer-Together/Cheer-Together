package com.ssafy.cheertogether.article.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.article.domain.UnLike;

public interface UnLikeRepository extends JpaRepository<UnLike, Long> {
	Optional<UnLike> findUnLikeByArticle_IdAndMember_Email(Long articleId, String memberEmail);
}

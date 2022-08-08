package com.ssafy.cheertogether.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.cheertogether.article.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}

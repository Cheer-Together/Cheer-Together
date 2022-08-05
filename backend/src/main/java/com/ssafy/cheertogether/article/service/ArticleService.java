package com.ssafy.cheertogether.article.service;

import static com.ssafy.cheertogether.article.ArticleConstant.*;
import static com.ssafy.cheertogether.member.MemberConstant.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cheertogether.article.domain.Article;
import com.ssafy.cheertogether.article.domain.Likes;
import com.ssafy.cheertogether.article.domain.Reply;
import com.ssafy.cheertogether.article.domain.UnLike;
import com.ssafy.cheertogether.article.dto.ArticleModifyRequest;
import com.ssafy.cheertogether.article.dto.ArticleRegisterRequest;
import com.ssafy.cheertogether.article.dto.ArticleResponse;
import com.ssafy.cheertogether.article.dto.ReplyRequest;
import com.ssafy.cheertogether.article.exception.AlreadyLikesUnlikeException;
import com.ssafy.cheertogether.article.repository.ArticleRepository;
import com.ssafy.cheertogether.article.repository.LikesRepository;
import com.ssafy.cheertogether.article.repository.ReplyRepository;
import com.ssafy.cheertogether.article.repository.UnLikeRepository;
import com.ssafy.cheertogether.auth.JwtTokenProvider;
import com.ssafy.cheertogether.league.repository.LeagueRepository;
import com.ssafy.cheertogether.member.domain.Member;
import com.ssafy.cheertogether.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final MemberRepository memberRepository;
	private final LeagueRepository leagueRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final LikesRepository likesRepository;
	private final UnLikeRepository unLikeRepository;
	private final ReplyRepository replyRepository;

	public void regist(ArticleRegisterRequest articleRegisterRequest,String jwtToken) {
		Article article = Article.from(articleRegisterRequest);
		article.setCreateDate();
		article.setMember(findMemberByJwtToken(jwtToken));
		article.setLeague(leagueRepository.findLeagueByApiId(articleRegisterRequest.getLeagueApiId()).get());
		articleRepository.save(article);
	}

	@Transactional(readOnly = true)
	public List<ArticleResponse> findAll() {
		return articleRepository
			.findAll()
			.stream()
			.map(article -> new ArticleResponse(article))
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ArticleResponse> findByHeader(int leagueApiId) {
		return articleRepository
			.findArticlesByLeague_ApiId(leagueApiId)
			.stream()
			.map(article -> new ArticleResponse(article))
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ArticleResponse> findAllSearchByTitleContent(String keyword) {
		return articleRepository
			.findArticlesByTitleContainingOrContentContaining(keyword, keyword)
			.stream()
			.map(article -> new ArticleResponse(article))
			.collect(Collectors.toList());
	}

	public ArticleResponse modify(Long articleId, ArticleModifyRequest articleModifyRequest) {
		Article article = findArticleByArticleId(articleId);
		article.update(articleModifyRequest);
		article.setLeague(leagueRepository.findLeagueByApiId(articleModifyRequest.getLeagueApiId()).get());
		return new ArticleResponse(article);
	}

	public ArticleResponse detail(Long articleId) {
		Article article = findArticleByArticleId(articleId);
		article.uphit();
		return new ArticleResponse(article);
	}

	public void delete(Long articleId) {
		articleRepository
			.delete(findArticleByArticleId(articleId));
	}

	public Long likes(Long articleId, String jwtToken) {
		String memberEmail = jwtTokenProvider.getMemberEmail(jwtToken);
		likesRepository.findLikesByArticle_IdAndMember_Email(articleId, memberEmail)
			.ifPresent(likes -> {throw new AlreadyLikesUnlikeException();});
		Article article = findArticleByArticleId(articleId);
		article.uplikes();
		Likes likes = new Likes();
		likes.setArticle(article);
		likes.setMember(findMemberByJwtToken(jwtToken));
		likesRepository.save(likes);
		return article.getLikes();
	}

	public Long unLike(Long articleId, String jwtToken) {
		String memberEmail = jwtTokenProvider.getMemberEmail(jwtToken);
		unLikeRepository.findUnLikeByArticle_IdAndMember_Email(articleId, memberEmail)
			.ifPresent(unLike -> {throw new AlreadyLikesUnlikeException();});
		Article article = articleRepository
			.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException(MISSMATCH_ID_ERROR_MESSAGE));
		article.unlike();
		UnLike unLike = new UnLike();
		unLike.setArticle(article);
		unLike.setMember(findMemberByJwtToken(jwtToken));
		unLikeRepository.save(unLike);
		return article.getUnlike();
	}

	public void replyRegist(Long articleId, ReplyRequest replyRequest) {
		Reply reply = Reply.from(replyRequest);
		reply.setArticle(findArticleByArticleId(articleId));
		reply.setCreateDate();
		replyRepository.save(reply);
	}

	public void replyModify(Long replyId, ReplyRequest replyRequest) {
		Reply reply = replyRepository.findById(replyId)
			.orElseThrow(() -> new IllegalArgumentException(MISSMATCH_ID_ERROR_MESSAGE));
		reply.update(replyRequest);
	}

	public void replyDelete(Long replyId) {
		replyRepository.deleteById(replyId);
	}

	private Member findMemberByJwtToken(String jwtToken) {
		return memberRepository
			.findByEmail(jwtTokenProvider
				.getMemberEmail(jwtToken))
			.orElseThrow(() -> new IllegalArgumentException(MISMATCH_EMAIL_ERROR_MESSAGE));
	}

	private Article findArticleByArticleId(Long articleId) {
		return articleRepository
			.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException(MISSMATCH_ID_ERROR_MESSAGE));
	}
}

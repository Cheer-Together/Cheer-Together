package com.ssafy.cheertogether.article.service;

import static com.ssafy.cheertogether.article.ArticleConstant.*;
import static com.ssafy.cheertogether.member.MemberConstant.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
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
import com.ssafy.cheertogether.article.exception.NoAccessException;
import com.ssafy.cheertogether.article.repository.ArticleRepository;
import com.ssafy.cheertogether.article.repository.LikesRepository;
import com.ssafy.cheertogether.article.repository.ReplyRepository;
import com.ssafy.cheertogether.article.repository.UnLikeRepository;
import com.ssafy.cheertogether.league.repository.LeagueRepository;
import com.ssafy.cheertogether.member.JwtTokenProvider;
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

	public void regist(ArticleRegisterRequest articleRegisterRequest, String jwtToken) {
		Article article = Article.from(articleRegisterRequest, findMemberByJwtToken(jwtToken), leagueRepository.findLeagueByApiId(articleRegisterRequest.getLeagueApiId()).get());
		article.setCreateDate();
		articleRepository.save(article);
	}

	@Transactional(readOnly = true)
	public List<ArticleResponse> findAll() {
		return articleRepository
			.findAll(Sort.by(Sort.Direction.DESC, "id"))
			.stream()
			.map(article -> new ArticleResponse(article))
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ArticleResponse> findByHeader(Long leagueApiId) {
		return articleRepository
			.findArticlesByLeague_ApiId(leagueApiId, Sort.by(Sort.Direction.DESC, "id"))
			.stream()
			.map(article -> new ArticleResponse(article))
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ArticleResponse> findAllSearchByTitleContent(String keyword) {
		return articleRepository
			.findArticlesByTitleContainingOrContentContaining(keyword, keyword, Sort.by(Sort.Direction.DESC, "id"))
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
		Long memberId = Long.parseLong(jwtTokenProvider.getMemberId(jwtToken));
		likesRepository.findLikesByArticle_IdAndMember_Id(articleId, memberId)
			.ifPresent(likes -> {
				throw new AlreadyLikesUnlikeException();
			});
		Article article = findArticleByArticleId(articleId);
		article.uplikes();
		Likes likes = new Likes();
		likes.setArticle(article);
		likes.setMember(findMemberByJwtToken(jwtToken));
		likesRepository.save(likes);
		return article.getLikes();
	}

	public Long unLike(Long articleId, String jwtToken) {
		Long memberId = Long.parseLong(jwtTokenProvider.getMemberId(jwtToken));
		unLikeRepository.findUnLikeByArticle_IdAndMember_Id(articleId, memberId)
			.ifPresent(unLike -> {
				throw new AlreadyLikesUnlikeException();
			});
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

	public void replyRegist(Long articleId, ReplyRequest replyRequest, String jwtToken) {

		Reply reply = Reply.from(replyRequest);
		reply.setMember(findMemberByJwtToken(jwtToken));
		reply.setArticle(findArticleByArticleId(articleId));
		reply.setCreateDate();
		findArticleByArticleId(articleId).addReply(reply);
		replyRepository.save(reply);
	}

	public void replyModify(Long replyId, ReplyRequest replyRequest, String jwtToken) {
		Reply reply = replyRepository.findById(replyId)
			.orElseThrow(() -> new IllegalArgumentException(MISSMATCH_ID_ERROR_MESSAGE));
		if (reply.getMember().getId() != Long.parseLong(jwtTokenProvider.getMemberId(jwtToken))) {
			throw new NoAccessException();
		}
		reply.update(replyRequest);
	}

	public void replyDelete(Long replyId, String jwtToken) {
		Reply reply = replyRepository.findById(replyId)
			.orElseThrow(() -> new IllegalArgumentException(MISSMATCH_ID_ERROR_MESSAGE));
		if (reply.getMember().getId() != Long.parseLong(jwtTokenProvider.getMemberId(jwtToken))) {
			throw new NoAccessException();
		}
		replyRepository.deleteById(replyId);
	}

	private Member findMemberByJwtToken(String jwtToken) {
		return memberRepository
			.findById(Long.parseLong(jwtTokenProvider.getMemberId(jwtToken)))
			.orElseThrow(() -> new IllegalArgumentException(MISMATCH_EMAIL_ERROR_MESSAGE));
	}

	private Article findArticleByArticleId(Long articleId) {
		return articleRepository
			.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException(MISSMATCH_ID_ERROR_MESSAGE));
	}
}

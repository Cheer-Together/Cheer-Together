package com.ssafy.cheertogether.article.controller;

import static com.ssafy.cheertogether.article.ArticleConstant.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.article.dto.ArticleModifyRequest;
import com.ssafy.cheertogether.article.dto.ArticleRegisterRequest;
import com.ssafy.cheertogether.article.dto.ArticleResponse;
import com.ssafy.cheertogether.article.dto.ReplyRequest;
import com.ssafy.cheertogether.article.service.ArticleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

	private final ArticleService articleService;

	@PostMapping
	@ApiOperation(value = "글 등록", notes = "게시판 - 글 등록")
	public ResponseEntity<String> regist(@RequestBody ArticleRegisterRequest articleRegisterRequest, @ApiParam(value = "jwt토큰", required = true, example = "jwt토큰") @RequestParam String jwtToken) {
		articleService.regist(articleRegisterRequest, jwtToken);
		return new ResponseEntity<>(REGIST_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@GetMapping
	@ApiOperation(value = "글 목록 조회", notes = "게시판 - 글 목록 조회")
	public ResponseEntity<List<ArticleResponse>> findAll() {
		return new ResponseEntity<>(articleService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/header/{leagueApiId}")
	@ApiOperation(value = "헤더 별 글 목록 조회", notes = "게시판 - 헤더 별 글 목록 조회")
	public ResponseEntity<List<ArticleResponse>> findByLeagueApiId(@PathVariable int leagueApiId) {
		return new ResponseEntity<>(articleService.findByHeader(leagueApiId), HttpStatus.OK);
	}

	@GetMapping("/search")
	@ApiOperation(value = "검색한 단어를 포함한 글 목록 조회", notes = "게시판 - 검색한 단어를 포함한 글 목록 조회")
	public ResponseEntity<List<ArticleResponse>> findAllByKeyWord(@ApiParam(value = "검색 단어", required = true, example = "토트넘") @RequestParam String keyword) {
		return new ResponseEntity<>(articleService.findAllSearchByTitleContent(keyword), HttpStatus.OK);
	}

	@PostMapping("/{articleId}")
	@ApiOperation(value = "게시글 수정", notes = "게시판 - 게시글 수정")
	public ResponseEntity<ArticleResponse> modify(@PathVariable Long articleId, @RequestBody ArticleModifyRequest articleModifyRequest) {
		return new ResponseEntity<>(articleService.modify(articleId, articleModifyRequest), HttpStatus.OK);
	}

	@GetMapping("/{articleId}")
	@ApiOperation(value = "게시글 상세 조회", notes = "게시판 - 게시글 상세 조회")
	public ResponseEntity<ArticleResponse> detail(@PathVariable Long articleId) {
		return new ResponseEntity<>(articleService.detail(articleId), HttpStatus.OK);
	}

	@DeleteMapping("/{articleId}")
	@ApiOperation(value = "게시글 삭제", notes = "게시판 - 게시글 삭제")
	public ResponseEntity<String> delete(@PathVariable Long articleId) {
		articleService.delete(articleId);
		return new ResponseEntity<>(DELETE_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@PostMapping("/{articleId}/like")
	@ApiOperation(value = "게시글 좋아요 등록", notes = "게시판 - 게시글 좋아요 등록")
	public ResponseEntity<Long> like(@PathVariable Long articleId,@ApiParam(value = "jwt토큰", required = true, example = "jwt토큰") @RequestParam String jwtToken) {
		return new ResponseEntity<>(articleService.likes(articleId, jwtToken), HttpStatus.OK);
	}

	@PostMapping("/{articleId}/unlike")
	@ApiOperation(value = "게시글 싫어요 등록", notes = "게시판 - 게시글 싫어요 등록")
	public ResponseEntity<Long> unLike(@PathVariable Long articleId,@ApiParam(value = "jwt토큰", required = true, example = "jwt토큰") @RequestParam String jwtToken) {
		return new ResponseEntity<>(articleService.unLike(articleId, jwtToken), HttpStatus.OK);
	}

	@PostMapping("/{articleId}/reply")
	@ApiOperation(value = "게시글 댓글 등록", notes = "게시판 - 게시글 댓글 등록")
	public ResponseEntity<String> registReply(@PathVariable Long articleId, @RequestBody ReplyRequest replyRequest) {
		articleService.replyRegist(articleId, replyRequest);
		return new ResponseEntity<>(REGIST_REPLY_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@PostMapping("/reply/{replyId}")
	@ApiOperation(value = "게시글 댓글 수정", notes = "게시판 - 게시글 댓글 수정")
	public ResponseEntity<String> modifyReply(@PathVariable Long replyId, @RequestBody ReplyRequest replyRequest) {
		articleService.replyModify(replyId, replyRequest);
		return new ResponseEntity<>(MODIFY_REPLY_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@DeleteMapping("/reply/{replyId}")
	@ApiOperation(value = "게시글 댓글 삭제", notes = "게시판 - 게시글 댓글 삭제")
	public ResponseEntity<String> deleteReply(@PathVariable Long replyId) {
		articleService.replyDelete(replyId);
		return new ResponseEntity<>(DELETE_REPLY_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}
}

package com.ssafy.cheertogether.article.controller;

import static com.ssafy.cheertogether.article.ArticleConstant.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cheertogether.article.dto.ArticleRegisterRequest;
import com.ssafy.cheertogether.article.dto.ArticleResponse;
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
	public ResponseEntity<String> regist(@RequestBody ArticleRegisterRequest articleRegisterRequest) {
		articleService.regist(articleRegisterRequest);
		return new ResponseEntity<>(REGIST_SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	}

	@GetMapping
	@ApiOperation(value = "글 목록 조회", notes = "게시판 - 글 목록 조회")
	public ResponseEntity<List<ArticleResponse>> findAll() {
		return new ResponseEntity<>(articleService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{leagueApiId}")
	@ApiOperation(value = "헤더 별 글 목록 조회", notes = "게시판 - 헤더 별 글 목록 조회")
	public ResponseEntity<List<ArticleResponse>> findByLeagueApiId(@PathVariable int leagueApiId) {
		return new ResponseEntity<>(articleService.findByHeader(leagueApiId), HttpStatus.OK);
	}

	@GetMapping("/search")
	@ApiOperation(value = "검색한 단어를 포함한 글 목록 조회", notes = "게시판 - 검색한 단어를 포함한 글 목록 조회")
	public ResponseEntity<List<ArticleResponse>> findAllByKeyWord(@ApiParam(value = "검색 단어", required = true, example = "토트넘") @RequestParam String keyword) {
		return new ResponseEntity<>(articleService.findAllSearchByTitleContent(keyword), HttpStatus.OK);
	}

}

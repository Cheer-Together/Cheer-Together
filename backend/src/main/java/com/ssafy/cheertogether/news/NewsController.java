package com.ssafy.cheertogether.news;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

	private final NaverCrawler crawler;

	@GetMapping
	@ApiOperation(value = "최신 10개 뉴스 검색", notes = "네이버 검색 뉴스 api에서 최신 뉴스 10개 불러오기")
	public ResponseEntity<?> getLatestNews(
		@ApiParam(value = "검색 토픽", required = true, example = "프리미어리그 분데스리가 세리에a") @RequestParam String subject) {
		return new ResponseEntity<>(crawler.getNews(subject), HttpStatus.OK);
	}
}

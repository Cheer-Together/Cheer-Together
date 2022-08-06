package com.ssafy.cheertogether.news;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

	private final NaverCrawler crawler;

	@GetMapping
	public ResponseEntity<?> getLatestNews(@RequestParam String subject) {
		return new ResponseEntity<>(crawler.getNews(subject), HttpStatus.OK);
	}
}

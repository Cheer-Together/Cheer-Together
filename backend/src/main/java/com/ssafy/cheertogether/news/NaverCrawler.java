package com.ssafy.cheertogether.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 네이버 뉴스 크롤링
 */
@Component
public class NaverCrawler {
	static final String baseUrl = "https://openapi.naver.com/v1/search/news.json?query=";

	@Value("${naver.search.api.clientId}")
	private String clientId;

	@Value("${naver.search.api.clientSecret}")
	private String clientSecret;

	public List<Map<String, Object>> getNews(String subject) {
		String response = search(subject);
		String[] fields = {"title", "link"};
		Map<String, Object> result = getResult(response, fields);
		return (List<Map<String, Object>>)result.get("result");
	}

	private String search(String subject) {
		try {
			String text = URLEncoder.encode(subject, "UTF8");
			String apiURL = baseUrl + text + "&display=10&start=1&sort=date"; //뉴스의 json 결과
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("X-Naver-Client-Id", clientId);
			conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = conn.getResponseCode();

			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			return response.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, Object> getResult(String response, String[] fields) {
		Map<String, Object> rtnObj = new HashMap<>();
		try {
			JSONParser parser = new JSONParser();
			JSONObject result = (JSONObject)parser.parse(response);

			JSONArray items = (JSONArray)result.get("items");
			List<Map<String, Object>> itemList = new ArrayList<>();

			for (Object o : items) {
				JSONObject item = (JSONObject)o;
				Map<String, Object> itemMap = new HashMap<>();

				for (String field : fields) {
					String filtered = String.valueOf(item.get(field)).replace("<b>", "");
					filtered = filtered.replace("</b>", "");
					itemMap.put(field, filtered);
				}
				itemList.add(itemMap);
			}
			rtnObj.put("result", itemList);
		} catch (Exception e) {
			System.out.println("getResult error -> " + "파싱 실패, " + e.getMessage());
		}
		return rtnObj;
	}
}

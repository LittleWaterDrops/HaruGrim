package com.yh.util;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Component
public class OpenAIUtil {

	@Value("${openai.api.key}")
	private String apiKey;

	@Value("${openai.api.url}")
	private String apiUrl;

	private final RestTemplate restTemplate;

	public OpenAIUtil(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	

	public String generateImageFromReview(String title, String content) {
		// 여러 스타일 정의
	    String[] styles = {
	        "A bright and cheerful cartoon-style diary illustration.",
	        "A bright and cheerful cartoon-style diary illustration. The artwork features warm tones, cute details, and a focus on daily life. The characters and objects are drawn in a simple, colorful, and soft style, evoking a cozy and happy feeling. Pink and white are the primary colors, with additional pastel accents.\"",
	        "A futuristic sci-fi digital artwork with neon accents.",
	        "A soft watercolor painting evoking calm and serenity."
	    };

	    // 랜덤 스타일 선택
	    String baseStyle = styles[new Random().nextInt(styles.length)];

	    // 동적 프롬프트 작성
	    String dynamicDetails = String.format(" The illustration shows: inspired by the following:\n- Title: %s\n- Content: %s.", title, content);

	    // 최종 프롬프트
	    String prompt = baseStyle + dynamicDetails;

	    // OpenAI API 호출
	    
	    return generateImage(prompt);
	}


	public String generateImage(String prompt) {
//		return "https://dummyimage.com/600x400/000/fff&text=MockImage";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + apiKey);
		headers.set("Content-Type", "application/json");
		
		
		System.out.println("prompt :" + prompt);
		
		Map<String, Object> requestBody = Map.of("prompt", prompt,
				"n", 1, // 한 번에 생성할 이미지 수
				"size", "512x512" // 이미지 크기
		);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		// OpenAI API 호출
		ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

		// OpenAI 응답에서 URL 추출
		List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");
		return (String) data.get(0).get("url");
	}

	@PostConstruct
	public void checkProperties() {
		System.out.println("OpenAI API Key: " + apiKey);
		System.out.println("OpenAI API URL: " + apiUrl);
	}
}

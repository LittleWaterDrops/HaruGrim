package com.yh.util;

import java.util.List;
import java.util.Map;

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

	public String generateImage(String prompt) {
//		return "https://dummyimage.com/600x400/000/fff&text=MockImage";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = Map.of(
            "prompt", prompt,
            "n", 1, // 한 번에 생성할 이미지 수
            "size", "1024x1024" // 이미지 크기
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        // OpenAI 응답에서 URL 추출
        List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");
        return (String) data.get(0).get("url");
	}

	public String generateImageFromReview(String title, String content) {
		// OpenAI API를 호출하여 이미지를 생성
		// 요청 데이터에는 title과 content를 조합하여 보낼 수 있음
		String prompt = "Create an elegant illustration based on the following:\n" + "Title: " + title + "\n"
				+ "Content: " + content;

		// 여기에 OpenAI API 호출 코드를 작성 (DALL·E 등)
		return generateImage(prompt);

//		// 예시 URL 반환 (실제 API 응답에서 받아온 URL을 반환해야 함)
//		return "https://example.com/generated-image-url";
	}
	
	 @PostConstruct
	    public void checkProperties() {
	        System.out.println("OpenAI API Key: " + apiKey);
	        System.out.println("OpenAI API URL: " + apiUrl);
	    }
}

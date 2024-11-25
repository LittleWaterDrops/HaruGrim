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

    @Value("${openai.api.url.image}")
    private String apiImageUrl;

    @Value("${openai.api.url.chat}")
    private String apiChatUrl;

    private final RestTemplate restTemplate;

    public OpenAIUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateImageFromReview(String title, String content) {
        // 1. title과 content를 영어로 번역
        String translatedTitle = generateTranslation(title);
        String translatedContent = generateTranslation(content);

        // 여러 스타일 정의
        String[] styles = {
            "A cute and cheerful cartoon-style diary illustration. Features warm pastel tones, soft outlines, and a cozy, whimsical atmosphere.",
            "A simple, flat-style illustration inspired by playful children's books. Uses warm colors and minimalistic design.",
            "A warm and friendly cartoon-style scene with smooth shading, soft edges, and a whimsical look."
        };

        // 랜덤 스타일 선택
        String baseStyle = styles[new Random().nextInt(styles.length)];

        // 동적 프롬프트 작성
        String dynamicDetails = String.format(
            " The illustration shows: inspired by the following:\n- Title: %s\n- Content: %s.", translatedTitle, translatedContent);

        // 최종 프롬프트
        String prompt = baseStyle + dynamicDetails;
        System.out.println(prompt);

        // OpenAI API 호출
        return generateImage(prompt);
    }

    public String generateImage(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = Map.of(
            "prompt", prompt,
            "n", 1, // 한 번에 생성할 이미지 수
            "size", "512x512" // 이미지 크기
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // OpenAI API 호출
        ResponseEntity<Map> response = restTemplate.exchange(apiImageUrl, HttpMethod.POST, entity, Map.class);

        // OpenAI 응답에서 URL 추출
        List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");
        return (String) data.get(0).get("url");
    }

    public String generateTranslation(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        String prompt = String.format("Translate the following text into English: \"%s\"", text);

        Map<String, Object> requestBody = Map.of(
            "model", "gpt-3.5-turbo",
            "messages", List.of(
                Map.of("role", "system", "content", "You are a professional translator."),
                Map.of("role", "user", "content", prompt)
            )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // OpenAI API 호출
            ResponseEntity<Map> response = restTemplate.exchange(apiChatUrl, HttpMethod.POST, entity, Map.class);

            // 응답 구조 디버깅
            System.out.println("Translation API Response: " + response.getBody());

            // 응답 데이터 처리
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            String content = (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");

            return content.trim(); // 번역 결과 반환
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("번역 중 오류 발생: " + e.getMessage());
        }
    }


    @PostConstruct
    public void checkProperties() {
        System.out.println("OpenAI API Key: " + apiKey);
        System.out.println("OpenAI API Image URL: " + apiImageUrl);
        System.out.println("OpenAI API Chat URL: " + apiChatUrl);
    }
}

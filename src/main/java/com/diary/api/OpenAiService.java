package com.diary.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class OpenAiService {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String ask(String userInput) {
        String url = "https://api.openai.com/v1/chat/completions";

        // 요청 DTO 생성
        MessageDTO message = new MessageDTO("user", userInput);
        ChatRequestDTO request = new ChatRequestDTO(model, Collections.singletonList(message));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatRequestDTO> entity = new HttpEntity<>(request, headers);

        System.out.println("🔹 사용자 질문: " + userInput); // 디버깅 로그

        ResponseEntity<ChatResponseDTO> response =
                restTemplate.postForEntity(url, entity, ChatResponseDTO.class);

        if (response.getBody() == null) {
            System.out.println("❌ OpenAI 응답 body가 null임!");
            return "OpenAI 응답 오류: body 없음";
        }

        if (response.getBody().getChoices() == null || response.getBody().getChoices().isEmpty()) {
            System.out.println("❌ OpenAI 응답에 choices가 없음!");
            return "OpenAI 응답 오류: 선택지 없음";
        }

        String result = response.getBody().getChoices().get(0).getMessage().getContent();
        System.out.println("✅ GPT 응답: " + result);
        return result;
    }
}

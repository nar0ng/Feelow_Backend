package com.feelow.Feelow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelow.Feelow.domain.Chat;
import com.feelow.Feelow.domain.Member;
import com.feelow.Feelow.dto.ChatRequest;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.MemberRepository;
import com.feelow.Feelow.service.ChatService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/chat/{memberId}/{date}")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping(produces = "application/json; charset=utf8")
    public ResponseEntity<String> chat(
            @RequestBody ChatRequest chatRequest,
            @PathVariable Long memberId,
            @PathVariable String date) {

        Member member = memberRepository.findByMemberId(memberId).orElse(null);

        if (member == null) {
            // 학생이 존재하지 않는 경우 404 Not Found 반환
            return ResponseEntity.notFound().build();
        }

        String flaskUrl = "http://127.0.0.1:5000/api/chat";

        // Explicitly set TLS version to TLSv1.2
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
        } catch (Exception e) {
            // Handle the exception appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Create a custom request factory with the specified SSLContext
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
        );

        // Create a RestTemplate with the custom request factory
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 엔터티 생성
        HttpEntity<ChatRequest> entity = new HttpEntity<>(chatRequest, headers);

        try {
            // Flask 서버에 POST 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.getBody());

                String input = jsonNode.get("input").asText();
                String responseText = jsonNode.get("response").asText();

                JsonNode sentimentNode = jsonNode.get("sentiment");
                if (sentimentNode.isArray() && sentimentNode.size() > 0) {
                    double positiveScore = 0.0;

                    for (JsonNode node : sentimentNode) {
                        String label = node.get("label").asText();
                        if ("positive".equals(label)) {
                            positiveScore = node.get("score").asDouble();
                            break;
                        }
                    }

                    Chat chat = new Chat();
                    chat.setPositiveScore(positiveScore);
                    chat.setInput(input);
                    chat.setResponse(responseText);
                    chat.setDate(date);
                    chat.setMember(member);

                    chat.setInputTime(LocalDateTime.now());

                    chatService.saveChat(chat);
                } else {
                    System.out.println("No sentiment information found");
                }

                return response;
            } else {
                // Flask 서버 응답이 성공하지 않은 경우 500 Internal Server Error 반환
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (JsonProcessingException e) {
            // JSON 매핑 중 오류 발생 시 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("")
    public ResponseDto<List<Chat>> getChatRecords(
            @PathVariable Long memberId,
            @PathVariable String date) {
        List<Chat> chatRecords = chatService.getChatRecords(memberId, date).getData();
        return ResponseDto.successChat("Chat records retrieved successfully", chatRecords);
    }
}

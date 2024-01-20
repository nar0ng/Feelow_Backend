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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/chat/{memberId}/{date}")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping(produces = "application/json; charset=utf8")
    public ResponseEntity<String> Chat(@RequestBody ChatRequest chatRequest,
                                       @PathVariable Long memberId,
                                       @PathVariable String date) {

        Member member = memberRepository.findByMemberId(memberId).orElse(null);

        if (member == null) {
            // 학생이 존재하지 않는 경우 404 Not Found 반환
            return ResponseEntity.notFound().build();
        }

        String flaskUrl = "http://192.168.0.23:5001/api/chat";

        WebClient webClient = WebClient.create();

        try {
            // Use WebClient to send the POST request
            String response = webClient.post()
                    .uri(flaskUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(chatRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // blocking for simplicity, consider using subscribe instead

            if (response != null) {
                // Handle the response

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response);

                String input = jsonNode.get("input").asText();
                String responseText = jsonNode.get("response").asText();

                JsonNode sentimentNode = jsonNode.get("sentiment");
                if (sentimentNode.isArray() && sentimentNode.size() > 0) {
                    double positiveScore = 0.0;

                    for (JsonNode node: sentimentNode){
                        String label = node.get("label").asText();
                        if ("positive".equals(label)){
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

                return ResponseEntity.ok(response);
            } else {
                // WebClient response is null, handle the error
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (JsonProcessingException e) {
            // JSON mapping error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("")
    public ResponseDto<List<Chat>> getChatRecords(
            @PathVariable Long memberId,
            @PathVariable String date
    ) {
        List<Chat> chatRecords = chatService.getChatRecords(memberId, date).getData();
        return ResponseDto.successChat("Chat records retrieved successfully", chatRecords);
    }
}

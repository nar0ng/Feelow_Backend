package com.feelow.Feelow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelow.Feelow.domain.Chat;
import com.feelow.Feelow.domain.Member;
import com.feelow.Feelow.domain.Student;
import com.feelow.Feelow.dto.ChatRequest;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.MemberRepository;
import com.feelow.Feelow.repository.StudentRepository;
import com.feelow.Feelow.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/chat/{memberId}/{date}")
public class ChatController {

    private final ChatService chatService;
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;

    @PostMapping(produces = "application/json; charset=utf8")
    public ResponseEntity<String> Chat(@RequestBody ChatRequest chatRequest,
                                       @PathVariable Long memberId,
                                       @PathVariable String date
    ) {

        Member member = memberRepository.findByMemberId(memberId).orElse(null);
        Student student = studentRepository.findByMember_memberId(memberId).orElse(null);

        // 학생이 존재하지 않는 경우
        if (member == null) {
            return ResponseEntity.notFound().build();
        }

        // ChatRequest에서 nickname 설정
        chatRequest.setNickname(student != null ? student.getNickname() : "");

        String flaskUrl = "http://127.0.0.1:5001/api/chat";
        RestTemplate restTemplate = new RestTemplate();

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
                assert student != null;
                String chatbotResponse = jsonNode.get("response").asText();

                JsonNode sentimentNode = jsonNode.get("senti_score");

                if (sentimentNode.isArray() && sentimentNode.size() > 0) {
                    double positiveScore = 0.0;

                    for (JsonNode node : sentimentNode) {
                        String label = node.get("label").asText();

                        if ("positive".equals(label)) {
                            positiveScore = node.get("score").asDouble();
                            break;
                        }
                    }

                    Chat chat = Chat.builder()
                            .positiveScore(positiveScore)
                            .input(input)
                            .response(chatbotResponse)
                            .inputTime(LocalDateTime.now())
                            .member(member)
                            .date(date)
                            .build();

                    chatService.saveChat(chat);

                } else {
                    System.out.println("No sentiment information found");
                }
                return response;
            } else {
                // Flask 서버 응답이 성공하지 않은 경우
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (JsonProcessingException e) {
            // JSON 매핑 중 오류 발생 시
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("")
    public ResponseDto<List<Chat>> getChatRecords(
            @PathVariable Long memberId,
            @PathVariable String date
    ) {
        List<Chat> chatRecords = chatService.getChatRecords(memberId, date);

        if (!(chatRecords == null)){
            return ResponseDto.successChat("Chat records retrieved successfully", chatRecords);
        }
        else {
            return ResponseDto.success("First", null);
        }

    }
}

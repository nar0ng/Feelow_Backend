package com.feelow.Feelow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelow.Feelow.domain.Chat;
import com.feelow.Feelow.domain.Student;
import com.feelow.Feelow.dto.ChatRequest;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.StudentRepository;
import com.feelow.Feelow.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/{studentId}/chat/{date}")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping(produces = "application/json; charset=utf8")
    public ResponseEntity<String> Chat(@RequestBody ChatRequest chatRequest,
                                       @PathVariable Long studentId,
                                       @PathVariable String date) {

        Student student = studentRepository.findById(studentId).orElse(null);

        if (student == null) {
            // 학생이 존재하지 않는 경우 404 Not Found 반환
            return ResponseEntity.notFound().build();
        }

        String flaskUrl = "http://127.0.0.1:5000/api/chat";

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
                String responseText = jsonNode.get("response").asText();
                String sentimentJson = jsonNode.get("sentiment").toString();

                JsonNode sentimentNode = new ObjectMapper().readTree(sentimentJson);
                String firstLabel = sentimentNode.get(0).get("label").asText();

                Chat chat = new Chat();
                chat.setSentiment(firstLabel);
                chat.setInput(input);
                chat.setResponse(responseText);
                chat.setDate(date);
                chat.setStudent(student);

                chatService.saveChat(chat);

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
            @PathVariable Long studentId,
            @PathVariable String date
    ) {
        List<Chat> chatRecords = chatService.getChatRecords(studentId, date).getData();
        return ResponseDto.successChat("Chat records retrieved successfully", chatRecords);
    }
}







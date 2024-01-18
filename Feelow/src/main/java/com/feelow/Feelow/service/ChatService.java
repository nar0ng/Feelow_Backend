package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.Chat;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.ChatRepository;
import com.feelow.Feelow.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public ResponseDto<List<Chat>> getChatRecords(Long studentId, String date) {
        List<Chat> chatRecords = chatRepository.findByStudentStudentIdAndDate(studentId, date);

        if (chatRecords != null && !chatRecords.isEmpty()) {
            return ResponseDto.successChat("채팅 레코드가 성공적으로 검색되었습니다", chatRecords);
        } else {
            return ResponseDto.failed(HttpStatus.NOT_FOUND, "지정된 사용자 및 날짜에 대한 채팅 레코드가 없습니다", null);
        }
    }


    @Transactional
    public ResponseDto<Chat> saveChat(Chat chat) {
        try {
            // 같은 날짜에 이미 채팅이 있는지 확인
            List<Chat> existingChats = chatRepository.findByStudentStudentIdAndDate(chat.getStudent().getStudentId(), chat.getDate());

            if (!existingChats.isEmpty()) {
                Chat existingChat = existingChats.get(existingChats.size() - 1);

                Chat newChat = new Chat();
                newChat.setStudent(existingChat.getStudent());
                newChat.setDate(existingChat.getDate());
                newChat.setConversationCount(existingChat.getConversationCount() + 1);
                newChat.setInput(chat.getInput());
                newChat.setResponse(chat.getResponse());
                newChat.setSentiment(chat.getSentiment());

                chatRepository.save(newChat);
                return ResponseDto.success("Chat saved successfully", chat);
            } else {
                chat.setConversationCount(1);
                Chat savedChat = chatRepository.save(chat);
                return ResponseDto.success("New chat saved successfully", savedChat);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.failed(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving chat", null);
        }
    }
}
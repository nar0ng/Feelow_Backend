package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.Chat;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public List<Chat> getChatRecords(Long memberId, String date) {
        List<Chat> chatRecords = chatRepository.findByMemberMemberIdAndDate(memberId, date);

        if (chatRecords != null && !chatRecords.isEmpty()) {
            return chatRecords;
        } else {
            return null;
        }
    }


    @Transactional
    public ResponseDto<Chat> saveChat(Chat chat) {
        try {
            // 같은 날짜에 이미 채팅이 있는지 확인
            List<Chat> existingChats = chatRepository.findByMemberMemberIdAndDate(chat.getMember().getMemberId(), chat.getDate());

            if (!existingChats.isEmpty()) {
                Chat existingChat = existingChats.get(existingChats.size() - 1);

                Chat newChat = new Chat();
                newChat.setMember(existingChat.getMember());
                newChat.setDate(existingChat.getDate());
                newChat.setConversationCount(existingChat.getConversationCount() + 1);
                newChat.setInput(chat.getInput());
                newChat.setResponse(chat.getResponse());
                newChat.setPositiveScore(chat.getPositiveScore());

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
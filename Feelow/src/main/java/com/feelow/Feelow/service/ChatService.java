package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.Chat;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

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
                chat.setResponse(getRandomResponse());
                chat.setInput(null);

                Chat firstChat = chatRepository.save(chat);

                return ResponseDto.success("First chat saved successfully", firstChat);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.failed(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving chat", null);
        }
    }

    private String getRandomResponse() {
        Random random = new Random();
        int randomNumber = random.nextInt(3);

        switch (randomNumber) {
            case 0:
                return "제일 좋아하는 색깔이 뭐야?";
            case 1:
                return "오늘 하루는 어땠어?";
            case 2:
                return "오늘 급식은 먹었어?";
            default:
                return "오늘 하루는 어땠어?";
        }
    }
}
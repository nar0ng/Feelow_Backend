package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.Chat;
import com.feelow.Feelow.dto.ResponseDto;
import com.feelow.Feelow.repository.ChatRepository;
import com.feelow.Feelow.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static com.feelow.Feelow.dto.MemberDto.getMember;

@Service
@AllArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

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
                Chat lastChat = existingChats.get(existingChats.size() - 1);
                Chat newChat = createNewChat(lastChat, chat);
                chatRepository.save(newChat);
                return ResponseDto.success("Chat saved successfully", newChat);
            } else {

                Chat firstChat = Chat.builder()
                        .conversationCount(1)
                        .date(chat.getDate())
                        .input(null)
                        .inputTime(LocalDateTime.now())
                        .member(chat.getMember())
                        .response(getRandomResponse())
                        .build();

                chatRepository.save(firstChat);

                Chat secondChat = Chat.builder()
                        .member(chat.getMember())
                        .conversationCount(2)
                        .date(chat.getDate())
                        .input(chat.getInput())
                        .inputTime(LocalDateTime.now())
                        .positiveScore(chat.getPositiveScore())
                        .response(chat.getResponse())
                        .build();

                chatRepository.save(secondChat);

                return ResponseDto.success("Firtst chat saved successfully", secondChat);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.failed(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving chat", null);
        }
    }

    private Chat createNewChat(Chat lastChat, Chat chat) {
        return Chat.builder()
                .member(lastChat.getMember())
                .conversationCount(lastChat.getConversationCount() + 1)
                .date(lastChat.getDate())
                .input(chat.getInput())
                .inputTime(chat.getInputTime())
                .positiveScore(chat.getPositiveScore())
                .response(chat.getResponse())
                .build();
    }

    private String getRandomResponse() {
        String[] randomResonses = {
                "제일 좋아하는 색깔이 뭐야?",
                "오늘 하루는 어땠어?",
                "오늘 급식은 먹었어?"
        };
        Random random = new Random();
        return randomResonses[random.nextInt(randomResonses.length)];
    }
}

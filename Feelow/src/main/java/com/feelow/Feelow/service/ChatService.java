package com.feelow.Feelow.service;

import com.feelow.Feelow.domain.dto.ChatResponseDto;
import com.feelow.Feelow.domain.entity.Chat;
import com.feelow.Feelow.domain.entity.Member;
import com.feelow.Feelow.domain.dto.ResponseDto;
import com.feelow.Feelow.domain.entity.Student;
import com.feelow.Feelow.repository.ChatRepository;
import com.feelow.Feelow.repository.MemberRepository;
import com.feelow.Feelow.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;

    public List<ChatResponseDto> getChatRecords(Long memberId, String date) {
        List<Chat> chatRecords = chatRepository.findByMemberMemberIdAndDate(memberId, date);
        List<ChatResponseDto> chatResponseDtos = new ArrayList<>();

        for (Chat chatRecord : chatRecords) {
            ChatResponseDto chatResponseDto = ChatResponseDto.builder()
                    .input(chatRecord.getInput())
                    .response(chatRecord.getResponse())
                    .point(chatRecord.getMember().getStudent().getPoint())
                    .build();
            chatResponseDtos.add(chatResponseDto);
        }

        if (chatRecords != null && !chatRecords.isEmpty()) {
            return chatResponseDtos;
        } else {
            Chat firstChat = creatFirstChat(memberId, date);
            if (!(firstChat == null)){
                chatRepository.save(firstChat);
                ChatResponseDto chatResponseDto = ChatResponseDto.builder()
                        .input(firstChat.getInput())
                        .response(firstChat.getResponse())
                        .point(firstChat.getMember().getStudent().getPoint() + 5)
                        .build();
                chatResponseDtos.add(chatResponseDto);
            }
            else {
                return Collections.emptyList();
            }
            return chatResponseDtos;
        }
    }


    @Transactional
    public ResponseDto<ChatResponseDto> saveChat(Chat chat) {
        try {
            List<Chat> existingChats = chatRepository.findByMemberMemberIdAndDate(chat.getMember().getMemberId(), chat.getDate());

            if (!existingChats.isEmpty()) {
                Chat lastChat = existingChats.get(existingChats.size() - 1);
                Chat newChat = createNewChat(lastChat, chat);
                chatRepository.save(newChat);

                if (((lastChat.getConversationCount()) + 1) % 10 == 0){
                    Member member = lastChat.getMember();
                    Student student = member.getStudent();
                    student.setPoint(student.getPoint() + 5);
                    studentRepository.save(student);
                }
                int point = newChat.getMember().getStudent().getPoint();
                ChatResponseDto chatResponseDto = ChatResponseDto.builder()
                        .input(newChat.getInput())
                        .response(newChat.getInput())
                        .response(newChat.getResponse())
                        .point(point)
                        .build();

                System.out.println(chatResponseDto);
                return ResponseDto.success("Chat saved successfully", chatResponseDto);
            } else {
                Chat firstChat = creatFirstChat(chat);
                chatRepository.save(firstChat);

                int point = firstChat.getMember().getStudent().getPoint();
                ChatResponseDto chatResponseDto = ChatResponseDto.builder()
                        .input(firstChat.getInput())
                        .response(firstChat.getInput())
                        .response(firstChat.getResponse())
                        .point(point)
                        .build();

                System.out.println(chatResponseDto);

                return ResponseDto.success("First chat saved successfully", chatResponseDto);
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


    private Chat creatFirstChat(Chat chat){
        return Chat.builder()
                .member(chat.getMember())
                .conversationCount(1)
                .date(chat.getDate())
                .input(null)
                .inputTime(LocalDateTime.now())
                .response(getRandomResponse())
                .build();
    }

    private Chat creatFirstChat(Long memberId, String date) {
        Optional<Member> memberOptional = memberRepository.findByMemberId(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            Student student = member.getStudent();
            student.setPoint(student.getPoint() + 5);
            studentRepository.save(student);

            return Chat.builder()
                    .member(member)
                    .conversationCount(1)
                    .date(date)
                    .input(null)
                    .inputTime(LocalDateTime.now())
                    .response(getRandomResponse())
                    .build();
        } else {
            return null;
        }
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

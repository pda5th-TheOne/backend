package pda5th.backend.theOne.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pda5th.backend.theOne.dto.QuestionResponseDto;
import pda5th.backend.theOne.entity.Reply;
import pda5th.backend.theOne.entity.Question;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.repository.DailyBoardRepository;
import pda5th.backend.theOne.repository.QuestionRepository;
import pda5th.backend.theOne.repository.ReplyRepository;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final QuestionRepository questionRepository;
    private final ReplyRepository replyRepository;

    // 답글 생성
    public String createReply(Integer questionId, User user, String replyContent) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Reply reply = Reply.builder()
                .content(replyContent)
                .createdAt(LocalDate.now())
                .updatedAt(null)
                .question(question)
                .user(user)
                .build();

        replyRepository.save(reply);

        return replyContent;
    }

}
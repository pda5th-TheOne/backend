package pda5th.backend.theOne.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pda5th.backend.theOne.controller.QuestionController;
import pda5th.backend.theOne.dto.QuestionResponseDto;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.Question;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.repository.DailyBoardRepository;
import pda5th.backend.theOne.repository.QuestionRepository;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final DailyBoardRepository dailyBoardRepository;
    private final QuestionRepository questionRepository;

    public String createQuestion(Integer boardId, User user, String questionContent) {
        DailyBoard dailyBoard = dailyBoardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        Question question = Question.builder()
                .content(questionContent)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .dailyBoard(dailyBoard)
                .user(user)
                .build();

        questionRepository.save(question);

        return questionContent;
    }

    // 질문 수정
    // userId 까지 확인해서 다르면 에러발생 (권한 없음)
    public String updateQuestion(Integer questionId, User user, String newQuestion) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        if (!question.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this question");
        }

        question.setContent(newQuestion);
        questionRepository.save(question);

        return newQuestion;
    }

    // 질문 삭제 후 questionId 반환
    // userId 까지 확인해서 다르면 에러 발생 (권한 없음)
    public Integer deleteQuestion(Integer questionId, User user) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        if (!question.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this question");
        }

        questionRepository.delete(question);

        return question.getId();
    }

    // 특정 게시판에 대한 질문 목록을 가져오는 메서드
    public List<QuestionResponseDto> getQuestionsByBoardId(Integer boardId) {
        List<Question> questions = questionRepository.findIdAndContentByBoardId(boardId);

        // Entity 리스트를 DTO 리스트로 변환
        return questions.stream()
                .map(QuestionResponseDto::fromEntity)
                .toList();
    }
}
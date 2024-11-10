package pda5th.backend.theOne.dto;

import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.Practice;
import pda5th.backend.theOne.entity.Question;
import pda5th.backend.theOne.entity.TIL;

import java.util.List;
import java.util.stream.Collectors;

public record DailyBoardDetails(
        DailyBoardDTO dailyBoard,
        List<PracticeDTO> top3Practices,
        List<TILDTO> top3TILs,
        List<QuestionDTO> top3Questions
) {
    // DailyBoard와 관련 엔티티를 받아서 DTO로 변환하는 메서드
    public static DailyBoardDetails fromEntity(
            DailyBoard dailyBoard,
            List<Practice> practices,
            List<TIL> tils,
            List<Question> questions
    ) {
        return new DailyBoardDetails(
                DailyBoardDTO.fromEntity(dailyBoard),
                practices.stream().map(PracticeDTO::fromEntity).collect(Collectors.toList()),
                tils.stream().map(TILDTO::fromEntity).collect(Collectors.toList()),
                questions.stream().map(QuestionDTO::fromEntity).collect(Collectors.toList())
        );
    }

    // DailyBoard DTO 내부 클래스
    public static record DailyBoardDTO(
            Integer id,
            String createdAt,
            String topic
    ) {
        public static DailyBoardDTO fromEntity(DailyBoard dailyBoard) {
            return new DailyBoardDTO(
                    dailyBoard.getId(),
                    dailyBoard.getCreatedAt().toString(),
                    dailyBoard.getTopic()
            );
        }
    }

    // Practice DTO 내부 클래스
    public static record PracticeDTO(
            Integer id,
            String title,
            String assignment,
            String answer
    ) {
        public static PracticeDTO fromEntity(Practice practice) {
            return new PracticeDTO(
                    practice.getId(),
                    practice.getTitle(),
                    practice.getAssignment(),
                    practice.getAnswer()
            );
        }
    }

    // TIL DTO 내부 클래스
    public static record TILDTO(
            Integer id,
            String title,
            String link
    ) {
        public static TILDTO fromEntity(TIL til) {
            return new TILDTO(
                    til.getId(),
                    til.getTitle(),
                    til.getLink()
            );
        }
    }

    // Question DTO 내부 클래스
    public static record QuestionDTO(
            Integer id,
            String content
    ) {
        public static QuestionDTO fromEntity(Question question) {
            return new QuestionDTO(
                    question.getId(),
                    question.getContent()
            );
        }
    }
}
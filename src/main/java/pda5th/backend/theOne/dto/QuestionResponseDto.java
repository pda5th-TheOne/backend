package pda5th.backend.theOne.dto;

import pda5th.backend.theOne.entity.Question;

// 특정 게시판에 대한 질문 조회 시 사용하는 DTO
public record QuestionResponseDto(
        Integer id,
        String content
) {
    // Entity를 DTO로 변환하는 메서드
    public static QuestionResponseDto fromEntity(Question question) {
        return new QuestionResponseDto(
                question.getId(),
                question.getContent()
        );
    }
}
package pda5th.backend.theOne.dto;

import pda5th.backend.theOne.entity.Question;
import pda5th.backend.theOne.entity.Reply;

public record ReplyResponseDto(
        Integer id,
        String content
) {
    // 엔티티를 DTO로 변환
    public static ReplyResponseDto fromEntity(Reply reply) {
        return new ReplyResponseDto(
                reply.getId(),
                reply.getContent()
        );
    }
}
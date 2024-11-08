package pda5th.backend.theOne.dto;

import java.util.List;

public record PracticeResponseDto(
        Integer id,
        String title,
        String assignment,
        String answer,
        List<UserPracticeDto> usersPractices
) {
    // Practice 엔티티와 UsersPractices 리스트를 활용하여 DTO를 생성하는 메서드
    public static PracticeResponseDto fromPracticeAndUsersPractices(
            pda5th.backend.theOne.entity.Practice practice,
            List<pda5th.backend.theOne.entity.UsersPractices> usersPractices) {

        return new PracticeResponseDto(
                practice.getId(),
                practice.getTitle(),
                practice.getAssignment(),
                practice.getAnswer(),
                usersPractices.stream()
                        .map(UserPracticeDto::fromEntity)
                        .toList()
        );
    }

    public record UserPracticeDto(
            Integer userId,
            String userName
    ) {
        public static UserPracticeDto fromEntity(pda5th.backend.theOne.entity.UsersPractices usersPractices) {
            return new UserPracticeDto(
                    usersPractices.getUser().getId(),
                    usersPractices.getUser().getName()
            );
        }
    }
}
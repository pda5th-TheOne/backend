package pda5th.backend.theOne.dto;

import java.util.List;

public record PracticeResponseDto(
        Integer id,
        String title,
        String assignment,
        String answer,
        List<UserPracticeDto> usersPractices
) {
    public static PracticeResponseDto fromUsersPractices(List<pda5th.backend.theOne.entity.UsersPractices> usersPractices) {
        var first = usersPractices.get(0);

        return new PracticeResponseDto(
                first.getPractice().getId(),
                first.getPractice().getTitle(),
                first.getPractice().getAssignment(),
                first.getPractice().getAnswer(),
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
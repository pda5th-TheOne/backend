package pda5th.backend.theOne.dto;

// boardId에 대한 practice 전체 조회 할 때, {id, title} 만 반환하기 위한 DTO
// 특정 boardId에 해당하는 Practice 목록 조회 시 사용할 DTO
public record PracticeSummaryDto(Integer id, String title) {

    public static PracticeSummaryDto fromEntity(pda5th.backend.theOne.entity.Practice practice) {
        return new PracticeSummaryDto(practice.getId(), practice.getTitle());
    }
}
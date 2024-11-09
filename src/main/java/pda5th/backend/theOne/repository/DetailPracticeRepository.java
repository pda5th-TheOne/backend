package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pda5th.backend.theOne.dto.PracticeSummaryDto;
import pda5th.backend.theOne.entity.Practice;

import java.util.List;

public interface DetailPracticeRepository extends JpaRepository<Practice, Integer> {
    // boardId에 해당하는 Practice 목록 조회
    @Query("SELECT new pda5th.backend.theOne.dto.PracticeSummaryDto(p.id, p.title) " +
            "FROM Practice p WHERE p.dailyBoard.id = :boardId")
    List<PracticeSummaryDto> findPracticeSummariesByBoardId(@Param("boardId") Integer boardId);
}

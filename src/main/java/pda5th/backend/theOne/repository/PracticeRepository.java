package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.Practice;

import java.util.List;

public interface PracticeRepository extends JpaRepository<Practice, Integer> {

    // 특정 DailyBoard에 속한 Practice 목록 중 상위 3개 조회
    @Query("SELECT p FROM Practice p WHERE p.dailyBoard = :dailyBoard ORDER BY p.createdAt DESC limit 3")
    List<Practice> findTop3ByDailyBoardOrderByCreatedAtDesc(DailyBoard dailyBoard);
}

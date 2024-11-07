
package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.TIL;

import java.util.List;

public interface TILRepository extends JpaRepository<TIL, Integer> {

    // 특정 DailyBoard에 속한 TIL 목록 중 상위 3개 조회
    @Query("SELECT t FROM TIL t WHERE t.dailyBoard = :dailyBoard ORDER BY t.createdAt DESC limit 3")
    List<TIL> findTop3ByDailyBoardOrderByCreatedAtDesc(DailyBoard dailyBoard);
}

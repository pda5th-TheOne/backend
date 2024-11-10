package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.Practice;

import java.util.List;

public interface PracticeRepository extends JpaRepository<Practice, Integer> {

//    // 특정 DailyBoard에 속한 Practice 목록 중 상위 3개 조회
//    @Query("SELECT p FROM Practice p WHERE p.dailyBoard = :dailyBoard ORDER BY p.createdAt DESC limit 3")

    // 특정 DailyBoard에 속한 상위 3개의 Practice 가져오기
    @Query(value = "SELECT * FROM practices p WHERE p.board_id = :boardId ORDER BY p.created_at DESC LIMIT 3", nativeQuery = true)
    List<Practice> findTop3ByDailyBoardOrderByCreatedAtDesc(@Param("boardId") Integer boardId);

//    List<Practice> findTop3ByDailyBoardOrderByCreatedAtDesc(DailyBoard dailyBoard);
////      List<Practice> findByDailyBoardOrderByCreatedAtDesc(DailyBoard dailyBoard);
}

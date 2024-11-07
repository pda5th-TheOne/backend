package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // 특정 DailyBoard에 속한 Question 목록 중 상위 3개 조회
    @Query("SELECT q FROM Question q WHERE q.dailyBoard = :dailyBoard ORDER BY q.createdAt DESC limit 3")
    List<Question> findTop3ByDailyBoardOrderByCreatedAtDesc(DailyBoard dailyBoard);
}

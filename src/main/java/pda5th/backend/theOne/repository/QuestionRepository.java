package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {


    @Query(value = "SELECT * FROM questions q WHERE q.board_id = :boardId ORDER BY q.created_at DESC LIMIT 3", nativeQuery = true)
    List<Question> findTop3ByDailyBoardOrderByCreatedAtDesc(@Param("boardId") Integer boardId);

    // 특정 게시판에 해당하는 모든 질문 조회 (id와 content만 가져오기)
    @Query("SELECT q FROM Question q WHERE q.dailyBoard.id = :boardId")
    List<Question> findIdAndContentByBoardId(@Param("boardId") Integer boardId);
}

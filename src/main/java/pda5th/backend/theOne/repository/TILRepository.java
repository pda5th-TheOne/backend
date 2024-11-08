
package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.TIL;

import java.util.List;
import java.util.Optional;

public interface TILRepository extends JpaRepository<TIL, Integer> {

    // 특정 DailyBoard에 속한 TIL 목록 중 상위 3개 조회
    @Query("SELECT t FROM TIL t WHERE t.dailyBoard = :dailyBoard ORDER BY t.createdAt DESC limit 3")
    List<TIL> findTop3ByDailyBoardOrderByCreatedAtDesc(DailyBoard dailyBoard);

    // 특정 TIL을 ID로 조회
    Optional<TIL> findById(Integer id);

    // 모든 TIL을 조회
    List<TIL> findAll();

    // 제목으로 TIL을 검색 (예시로 추가)
    List<TIL> findByTitle(String title);

    // 특정 유저의 TIL을 조회 (예시로 추가)
    List<TIL> findByUserId(Integer userId);
}
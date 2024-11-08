package pda5th.backend.theOne.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pda5th.backend.theOne.entity.DailyBoard;

public interface DailyBoardRepository extends JpaRepository<DailyBoard, Integer> {

    @Query("SELECT d FROM DailyBoard d ORDER BY d.createdAt DESC")
    Page<DailyBoard> findTop3ByOrderByCreatedAtDesc(Pageable pageable);
}

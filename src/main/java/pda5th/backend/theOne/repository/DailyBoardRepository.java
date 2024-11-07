package pda5th.backend.theOne.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.Practice;

public interface DailyBoardRepository extends JpaRepository<DailyBoard, Integer> {

    // 특정 DailyBoard에 속한 Practice 목록 페이징 처리

    Page<DailyBoard> findAll(Pageable pageable);
}

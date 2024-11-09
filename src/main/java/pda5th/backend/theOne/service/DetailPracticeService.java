package pda5th.backend.theOne.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.Practice;
import pda5th.backend.theOne.repository.DailyBoardRepository;
import pda5th.backend.theOne.repository.DetailPracticeRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DetailPracticeService {

    private final DetailPracticeRepository detailPracticeRepository;
    private final DailyBoardRepository dailyBoardRepository;

    // 실습(+) 버튼으로 title로 practice 테이블 생성하기
    public String createDetailPractice(Integer boardId, String title) {
        DailyBoard dailyBoard = dailyBoardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        Practice practice = Practice.builder()
                .title(title)
                .assignment(null)
                .answer(null)
                .createdAt(LocalDate.now())
                .dailyBoard(dailyBoard)
                .build();

        detailPracticeRepository.save(practice);

        return title;
    }
}

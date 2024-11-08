package pda5th.backend.theOne.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pda5th.backend.theOne.dto.DailyBoardCreateRequest;
import pda5th.backend.theOne.dto.DailyBoardDetails;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.Practice;
import pda5th.backend.theOne.entity.Question;
import pda5th.backend.theOne.entity.TIL;
import pda5th.backend.theOne.repository.DailyBoardRepository;
import pda5th.backend.theOne.repository.PracticeRepository;
import pda5th.backend.theOne.repository.QuestionRepository;
import pda5th.backend.theOne.repository.TILRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyBoardService {

    private final DailyBoardRepository dailyBoardRepository;
    private final PracticeRepository practiceRepository;
    private final TILRepository tilRepository;
    private final QuestionRepository questionRepository;


    // C 생성: 게시판 생성하기
    public void createBoard(DailyBoardCreateRequest request) {
        DailyBoard board = DailyBoard.builder()
                .createdAt(request.createdAt())
                .topic(request.topic())
                .build();

        dailyBoardRepository.save(board);
    }

    // R 조회: 게시판 모두 가져오기
    // 모든 DailyBoard와 관련된 상위 3개의 Practice, TIL, Question 가져오기
    public List<DailyBoardDetails> getTop3Boards(Pageable pageable) {
        Page<DailyBoard> dailyBoardsPage = dailyBoardRepository.findTop3ByOrderByCreatedAtDesc(pageable);
        List<DailyBoardDetails> boardDetailsList = new ArrayList<>();

        for (DailyBoard dailyBoard : dailyBoardsPage) {
            List<Practice> top3Practices = practiceRepository.findTop3ByDailyBoardOrderByCreatedAtDesc(dailyBoard);
            List<TIL> top3TILs = tilRepository.findTop3ByDailyBoardOrderByCreatedAtDesc(dailyBoard);
            List<Question> top3Questions = questionRepository.findTop3ByDailyBoardOrderByCreatedAtDesc(dailyBoard);

            DailyBoardDetails detailsDTO = new DailyBoardDetails(dailyBoard, top3Practices, top3TILs, top3Questions);
            boardDetailsList.add(detailsDTO);
        }
        return boardDetailsList;
    }


    // U 수정: 게시판 수정하기
    @Transactional
    public void updateBoard(Integer id, DailyBoardCreateRequest request) {
        // 기존 보드 삭제
        dailyBoardRepository.deleteById(id);
        // 새 보드 생성
        DailyBoard newBoard = DailyBoard.builder()
                .createdAt(LocalDate.now()) // 현재 날짜로 설정
                .topic(request.topic())     // request에서 주제 가져오기
                .build();
        dailyBoardRepository.save(newBoard);
    }


    // D 삭제: 게시판 삭제
    public void deleteBoard(Integer id) {
        dailyBoardRepository.deleteById(id);
    }
}

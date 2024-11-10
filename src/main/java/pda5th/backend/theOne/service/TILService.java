package pda5th.backend.theOne.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pda5th.backend.theOne.dto.TILCreateRequest;
import pda5th.backend.theOne.dto.TILResponse;
import pda5th.backend.theOne.dto.TILUpdateRequest;
import pda5th.backend.theOne.entity.*;
import pda5th.backend.theOne.repository.DailyBoardRepository;
import pda5th.backend.theOne.repository.QuestionRepository;
import pda5th.backend.theOne.repository.TILRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TILService {
    private final DailyBoardRepository boardRepository;
    private final TILRepository tilRepository;

    // Create
    public TILResponse createTIL(Integer boardId, User user, TILCreateRequest request) {
        DailyBoard board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        TIL til = TIL.builder()
                .dailyBoard(board)
                .title(request.title())
                .link(request.link())
                .user(user)
                .build();

        til = tilRepository.save(til);

        // TIL이 생성된 후 TILResponse를 반환
        return new TILResponse(til.getId(), til.getTitle(), til.getLink(), til.getUser().getId());
    }

    // Read: Get TIL by ID
    public List<TILResponse> getTILById(Integer id) {
        DailyBoard dailyBoard = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        List<TIL> tils = tilRepository.findByDailyBoard(dailyBoard);

        return tils.stream()
                .map(til -> new TILResponse(til.getId(), til.getTitle(), til.getLink(), til.getUser().getId()))
                .toList();
    }

    // Update: Update TIL by ID
    public TILResponse updateTIL(Integer id, User user, TILUpdateRequest request) {
        TIL til = tilRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));;

        if (!til.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this TIL");
        }

        til.setTitle(request.title());
        til.setLink(request.link());

        til = tilRepository.save(til);
        return new TILResponse(til.getId(), til.getTitle(), til.getLink(), til.getUser().getId());
    }

    // Delete: Delete TIL by ID
    public boolean deleteTIL(Integer id, User user) {
        TIL til = tilRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));;

        if (!til.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this TIL");
        }

        tilRepository.deleteById(id);
        return true;
    }
}

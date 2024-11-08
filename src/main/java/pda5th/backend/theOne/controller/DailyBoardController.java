package pda5th.backend.theOne.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.dto.DailyBoardCreateRequest;
import pda5th.backend.theOne.dto.DailyBoardDetails;
import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.service.DailyBoardService;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class DailyBoardController {

    private final DailyBoardService dailyBoardService;

    @PostMapping
    public ResponseEntity<Void> createBoard(@RequestBody DailyBoardCreateRequest request) {
        dailyBoardService.createBoard(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<DailyBoardDetails> getTop3Boards(@RequestParam(defaultValue = "0") int page) {

        return dailyBoardService.getTop3Boards (PageRequest.of(page, 3));
    }

    @PutMapping("/{id}")
    public void updateBoard(@PathVariable Integer id, @RequestBody DailyBoardCreateRequest request) {
        dailyBoardService.updateBoard(id, request);
    }
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Integer id) {
        dailyBoardService.deleteBoard(id);
    }
}

package pda5th.backend.theOne.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.common.security.UserPrincipal;
import pda5th.backend.theOne.dto.PracticeResponseDto;
import pda5th.backend.theOne.dto.PracticeSummaryDto;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.service.DetailPracticeService;
import pda5th.backend.theOne.service.PracticeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")

public class DetailPracticeController {
    private final DetailPracticeService detailPracticeService;

    // 특정 board에 대한 실습 추가
    @PostMapping("/boards/{id}/practices")
    @Operation(summary = "실습 추가", description = "실습(+) 버튼을 통한 실습 추가")
    public ResponseEntity<String> createDetailPractice(@PathVariable Integer id, @RequestBody String title) {
        String successTitle = detailPracticeService.createDetailPractice(id, title);

        return ResponseEntity.ok("success: " + successTitle);
    }

    // boradId를 통해 practice 전체 조회해서 {id, title}의 배열 형태로 반환
    @GetMapping("/boards/{id}/practices")
    @Operation(summary = "특정 Board에 대한 전체 실습 조회", description = "실습들의 {id, title}을 배열 형태로 반환")
    public ResponseEntity<List<PracticeSummaryDto>> getPracticesByBoardId(@PathVariable Integer id) {
        List<PracticeSummaryDto> practices = detailPracticeService.getPracticesByBoardId(id);

        return ResponseEntity.ok(practices);
    }

    // practiceId는 유일하니까 ..
    @PutMapping("/practices/{id}")
    @Operation(summary = "실습 제목 수정", description = "실습의 title 수정")
    public ResponseEntity<String> updatePracticeTitle(
            @PathVariable Integer id,
            @RequestBody String newTitle) {

        String updatedTitle = detailPracticeService.updatePracticeTitle(id, newTitle);

        return ResponseEntity.ok("Updated title: " + updatedTitle);
    }

    @DeleteMapping("/practices/{id}")
    @Operation(summary = "실습 삭제", description = "실습 삭제, users_practices에 값이 있어도 cascade 삭제 적용")
    public ResponseEntity<String> deletePractice (
            @PathVariable Integer id) {

        Integer deletedPracticeId = detailPracticeService.deletePractice(id);

        return ResponseEntity.ok("Deleted practiceId: " + deletedPracticeId);
    }

}

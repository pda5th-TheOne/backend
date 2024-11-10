package pda5th.backend.theOne.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.common.security.UserPrincipal;
import pda5th.backend.theOne.dto.PracticeResponseDto;
import pda5th.backend.theOne.dto.PracticeUpdateRequest;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.service.PracticeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/practices")
public class PracticeController {
    private final PracticeService practiceService;

    @GetMapping("/{id}")
    @Operation(summary = "실습에 대한 내용 GET", description = "(문제, 모범답안, 제출한 유저 ... )")
    public ResponseEntity<PracticeResponseDto> getPracticeDetail(@PathVariable Integer id) {
        PracticeResponseDto response = practiceService.getPracticeDetail(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/assignment")
    @Operation(summary = "문제 내용 수정", description = "편집 버튼을 누른 후, 문제 내용을 수정하고 완료버튼을 눌러 문제 내용을 수정합니다.")
    public ResponseEntity<String> updateAssignment(
            @PathVariable Integer id,
            @RequestBody PracticeUpdateRequest request) {
        String updatedAssignment = practiceService.updateAssignment(id, request.newContent());

        return ResponseEntity.ok("Updated assignment: " + updatedAssignment);
    }

    @PutMapping("{id}/answer")
    @Operation(summary = "모범답안 내용 수정", description = "편집 버튼을 누른 후, 문제 내용을 수정하고 완료버튼을 눌러 모범답안 내용을 수정합니다.")
    public ResponseEntity<String> updateAnswer(
            @PathVariable Integer id,
            @RequestBody PracticeUpdateRequest request) {
        String updatedAnswer = practiceService.updateAnswer(id, request.newContent());

        return ResponseEntity.ok("updated answer: " + updatedAnswer);
    }

    @PostMapping("{id}/users_practices")
    @Operation(summary = "제출하기", description = "제출(완료) 버튼을 누르면 userId를 저장하고 userName을 리턴합니다.")
    public ResponseEntity<String> createSumbitUser(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userPrincipal.getUser();
        String submitUserName = practiceService.createSubmitUser(id, user);

        return ResponseEntity.ok("submit user: " + submitUserName);
    }

    @DeleteMapping("{id}/users_practices")
    @Operation(summary = "제출 취소", description = "제출취소 버튼을 누르면 로그인된 userId값과 pracId를 비교하여 삭제 후, 삭제된 userId를 반환합니다.")
    public ResponseEntity<String> deleteSumbitUser(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userPrincipal.getUser();
        int deletedUserId = practiceService.deleteSumbitUser(id, user);

        return ResponseEntity.ok("deleted userId: " + deletedUserId);
    }
}

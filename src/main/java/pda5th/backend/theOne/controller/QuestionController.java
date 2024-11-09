package pda5th.backend.theOne.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.common.security.UserPrincipal;
import pda5th.backend.theOne.dto.QuestionResponseDto;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.service.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class QuestionController {
    private final QuestionService questionService;

    // 질문 추가
    @PostMapping("/boards/{id}/questions")
    @Operation(summary = "질문 추가", description = "질문을 추가하고 추가한 질문 내용 반환")
    public ResponseEntity<String> createQuestion(
            @PathVariable Integer id,
            @RequestBody String qusetionContent,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userPrincipal.getUser();
        String createdQuestion = questionService.createQuestion(id, user, qusetionContent);

        return ResponseEntity.ok("createdQuestion: " + createdQuestion);
    }

    // 특정 게시판에 대한 질문 전체 조회
    @GetMapping("/boards/{id}/questions")
    @Operation(summary = "질문 전체 조회", description = "특정 게시판에 대한 질문 배열 형태로 조회 {id, content}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByBoardId(@PathVariable Integer id) {
        List<QuestionResponseDto> questions = questionService.getQuestionsByBoardId(id);

        return ResponseEntity.ok(questions);
    }


    // 질문 수정
    @PutMapping("/questions/{id}")
    @Operation(summary = "질문 수정", description = "질문 수정하고 수정한 내용 반환 (userId가 다른 질문은 수정 불가 - 권한없음 (에러 처리))")
    public ResponseEntity<String> updateQuestion(
            @PathVariable Integer id,
            @RequestBody String newQuestion,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userPrincipal.getUser();
        String updatedQuestion = questionService.updateQuestion(id, user, newQuestion);

        return ResponseEntity.ok("updatedQuestion: " + updatedQuestion);
    }

    // 질문 삭제
    @DeleteMapping("/questions/{id}")
    @Operation(summary = "질문 삭제", description = "질문 삭제하고 삭제한 내용 반환 (userId가 다른 질문은 삭제 불가 - 권한없음 (에러 처리))")
    public ResponseEntity<String> deleteQuestion(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userPrincipal.getUser();
        Integer deletedQuestionId = questionService.deleteQuestion(id, user);

        return ResponseEntity.ok("deletedQuestionId: " + deletedQuestionId);
    }
}
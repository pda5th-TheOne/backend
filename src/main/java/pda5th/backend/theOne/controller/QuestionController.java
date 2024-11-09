package pda5th.backend.theOne.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.common.security.UserPrincipal;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.service.QuestionService;

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


}
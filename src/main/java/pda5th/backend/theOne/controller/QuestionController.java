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

}
package pda5th.backend.theOne.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.common.security.UserPrincipal;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.service.ReplyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ReplyController {
    private final ReplyService replyService;

    // 답글 추가
    @PostMapping("/questions/{id}/replies")
    @Operation(summary = "답글 추가", description = "답글을 추가하고 추가한 답글 내용 반환")
    public ResponseEntity<String> createReply(
            @PathVariable Integer id,
            @RequestBody String replyContent,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userPrincipal.getUser();
        String createdReply = replyService.createReply(id, user, replyContent);

        return ResponseEntity.ok("createdReply: " + createdReply);
    }

    // 답글 수정
    @PutMapping("/replies/{id}")
    @Operation(summary = "답글 수정", description = "답글을 수정하고 수정한 내용 반환 (userId가 다른 답글은 수정 불가 - 권한없음 (에러 처리)")
    public ResponseEntity<String> updateReply(
            @PathVariable Integer id,
            @RequestBody String newReply,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userPrincipal.getUser();
        String updatedReply = replyService.updateReply(id, user, newReply);

        return ResponseEntity.ok("updatedReply: " + updatedReply);
    }

    // 답글 삭제
    @DeleteMapping("/replies/{id}")
    @Operation(summary = "답글 삭제", description = "답글 삭제하고 삭제한 내용 반환 (userId가 다른 답글은 삭제 불가 - 권한없음 (에러 처리))")
    public ResponseEntity<String> deleteReply(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userPrincipal.getUser();
        Integer deletedReplyId = replyService.deleteReply(id, user);

        return ResponseEntity.ok("deletedReplyId: " + deletedReplyId);
    }
}
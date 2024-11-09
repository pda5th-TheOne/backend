package pda5th.backend.theOne.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.http.ResponseEntity;
import pda5th.backend.theOne.common.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/questions")
public class QuestionQueueController {
    private final List<Question> questionQueue = new CopyOnWriteArrayList<>();
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Operation(summary = "질문 큐 SSE 스트림", description = "질문 큐 상태를 SSE로 스트리밍합니다.")
    @GetMapping("/stream")
    public SseEmitter streamQuestions() {
        SseEmitter emitter = new SseEmitter(3600L * 1000L);  // 기본 타임아웃 시간 1시간
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));

        return emitter;
    }

    @Operation(summary = "손들기", description = "질문 큐에 사용자 추가(손들기).")
    @PostMapping
    public ResponseEntity<String> addQuestion(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        String userId = userPrincipal.getUser().getId().toString();
        String userName = userPrincipal.getUser().getName();

        if (questionQueue.stream().anyMatch(q -> q.getId().equals(userId))) {
            return ResponseEntity.badRequest().body("User is already in the queue");
        }

        questionQueue.add(new Question(userId, userName));
        sendUpdates();

        return ResponseEntity.ok("Question added by user: " + userName);
    }

    @Operation(summary = "손내리기", description = "질문 큐에서 사용자 제거(손내리기).")
    @DeleteMapping
    public ResponseEntity<String> removeQuestion(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        String userId = userPrincipal.getUser().getId().toString();  // ID 가져오기

        boolean removed = questionQueue.removeIf(q -> q.getId().equals(userId));

        if (!removed) {
            return ResponseEntity.badRequest().body("User is not in the queue");
        }

        sendUpdates();
        return ResponseEntity.ok("Question removed for user: " + userId);
    }

    @Operation(summary = "질문 큐 확인", description = "현재 질문 큐 상태를 반환합니다.")
    @GetMapping
    public ResponseEntity<List<Question>> getQuestions() {
        return ResponseEntity.ok(questionQueue);
    }

    // SSE로 질문 큐 업데이트 전송
    private void sendUpdates() {
        List<Question> currentQueue = List.copyOf(questionQueue);

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("questionQueueUpdate").data(currentQueue));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }

    // 질문 큐: ID와 이름을 저장하는 간단한 DTO로 구성
    private static class Question {
        private final String id;
        private final String name;

        public Question(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
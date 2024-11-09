package pda5th.backend.theOne.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pda5th.backend.theOne.dto.TimerRequest;
import pda5th.backend.theOne.dto.TimerResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/timer")
public class TimerController {

    private final Map<String, List<SseEmitter>> emitters = new ConcurrentHashMap<>();
    private final Map<String, TimerState> timers = new ConcurrentHashMap<>();

    @GetMapping("/{practiceId}/events")
    @Operation(summary = "타이머 상태 SSE 스트림", description = "practiceId에 대한 타이머 상태를 SSE로 스트리밍합니다.")
    public SseEmitter streamTimer(@PathVariable String practiceId) {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30분
        emitters.computeIfAbsent(practiceId, key -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> emitters.get(practiceId).remove(emitter));
        emitter.onTimeout(() -> emitters.get(practiceId).remove(emitter));
        emitter.onError((ex) -> emitters.get(practiceId).remove(emitter));

        return emitter;
    }

    @PostMapping("/{practiceId}/start")
    @Operation(summary = "타이머 시작", description = "주어진 시간(분)으로 타이머를 시작합니다.")
    public TimerResponse startTimer(@PathVariable String practiceId, @RequestBody TimerRequest request) {
        TimerState timer = new TimerState(request.minutes() * 60, true);
        timers.put(practiceId, timer);
        sendTimerUpdates(practiceId, new TimerResponse(timer.getTimeLeft(), timer.isRunning()));
        return new TimerResponse(timer.getTimeLeft(), timer.isRunning());
    }

    @DeleteMapping("/{practiceId}/reset")
    @Operation(summary = "타이머 리셋", description = "타이머를 초기 상태로 리셋합니다.")
    public TimerResponse resetTimer(@PathVariable String practiceId) {
        // 타이머 상태 초기화
        timers.remove(practiceId);
        sendTimerUpdates(practiceId, new TimerResponse(0, false));

        // practiceId에 연결된 모든 SseEmitter를 닫고 제거
        List<SseEmitter> emitterList = emitters.get(practiceId);
        if (emitterList != null) {
            emitterList.forEach(SseEmitter::complete);
            emitters.remove(practiceId);
        }

        return new TimerResponse(0, false);
    }

    @PatchMapping("/{practiceId}/pause")
    @Operation(summary = "타이머 일시정지", description = "타이머를 일시정지합니다.")
    public TimerResponse pauseTimer(@PathVariable String practiceId) {
        TimerState timer = timers.get(practiceId);
        if (timer != null) {
            timer.setRunning(false);
            sendTimerUpdates(practiceId, new TimerResponse(timer.getTimeLeft(), false));
            return new TimerResponse(timer.getTimeLeft(), false);
        }
        return new TimerResponse(0, false); // 타이머가 존재하지 않는 경우
    }

    @PatchMapping("/{practiceId}/resume")
    @Operation(summary = "타이머 재개", description = "일시정지된 타이머를 재개합니다.")
    public TimerResponse resumeTimer(@PathVariable String practiceId) {
        TimerState timer = timers.get(practiceId);
        if (timer != null) {
            timer.setRunning(true);
            sendTimerUpdates(practiceId, new TimerResponse(timer.getTimeLeft(), true));
            return new TimerResponse(timer.getTimeLeft(), true);
        }
        return new TimerResponse(0, false); // 타이머가 존재하지 않는 경우
    }

    @Scheduled(fixedRate = 1000) // 1초마다 타이머 감소 및 상태 푸시
    public void decrementTimers() {
        timers.forEach((practiceId, timer) -> {
            if (timer.isRunning() && timer.getTimeLeft() > 0) {
                timer.decrementTime();
                sendTimerUpdates(practiceId, new TimerResponse(timer.getTimeLeft(), timer.isRunning()));
            }
        });
    }

    private void sendTimerUpdates(String practiceId, TimerResponse response) {
        List<SseEmitter> emitterList = emitters.getOrDefault(practiceId, new CopyOnWriteArrayList<>());
        emitterList.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("timer-update")
                        .data(response));
            } catch (IOException e) {
                emitterList.remove(emitter); // 오류 발생 시 Emitter 제거
            }
        });
    }

    static class TimerState {
        private int timeLeft;
        private boolean isRunning;

        public TimerState(int timeLeft, boolean isRunning) {
            this.timeLeft = timeLeft;
            this.isRunning = isRunning;
        }

        public int getTimeLeft() {
            return timeLeft;
        }

        public void decrementTime() {
            if (timeLeft > 0) {
                timeLeft--;
            }
        }

        public boolean isRunning() {
            return isRunning;
        }

        public void setRunning(boolean running) {
            isRunning = running;
        }
    }
}

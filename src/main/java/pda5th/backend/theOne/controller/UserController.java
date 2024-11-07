package pda5th.backend.theOne.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.dto.SignUpRequest;
import pda5th.backend.theOne.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users") // 사용자 관련 API를 '/api/users' 경로로 그룹화
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "사용자 정보를 입력하여 회원가입을 진행합니다.")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }
}
package pda5th.backend.theOne.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.common.jwt.util.JwtUtil;
import pda5th.backend.theOne.common.security.UserPrincipal;
import pda5th.backend.theOne.dto.AuthRequest;
import pda5th.backend.theOne.dto.AuthResponse;
import pda5th.backend.theOne.dto.UserInfoResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth") // 모든 인증 관련 API를 '/api/auth' 경로로 그룹화
public class AuthController {

    private final AuthenticationManager authenticationManager; // Spring Security 인증 관리자
    private final JwtUtil jwtUtil; // JWT 유틸리티 클래스

    /**
     * 로그인하여 JWT 토큰을 발급받는 엔드포인트
     *
     * @param authRequest 사용자 로그인 요청 (username, password)
     * @return JWT 토큰 응답
     * @throws Exception 인증 실패 시 예외 발생
     */
    @PostMapping("/login")
    @Operation(summary = "로그인 및 JWT 토큰 발급", description = "사용자 이름과 비밀번호를 입력하여 JWT 토큰을 발급받습니다.")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // 사용자 인증 수행
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password", e); // 인증 실패 시 예외 처리
        }

        // 인증 성공 시 JWT 토큰 생성 및 반환
        String token = jwtUtil.generateToken(authRequest.username());
        return new AuthResponse(token); // JWT 토큰을 담은 응답 객체 반환
    }

    /**
     * JWT 토큰의 유효성을 확인하는 엔드포인트
     *
     * @return 간단한 메시지 (JWT가 유효한 경우)
     */
    @GetMapping("/validate-token")
    @Operation(summary = "JWT 토큰 유효성 확인", description = "발급받은 JWT 토큰이 유효한지 확인하고, 사용자 정보를 반환합니다.")
    public ResponseEntity<UserInfoResponse> validateToken(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userId =  userPrincipal.getUser().getId();
        String username = userPrincipal.getUser().getName();
        return ResponseEntity.ok(new UserInfoResponse(userId, username));
    }

}
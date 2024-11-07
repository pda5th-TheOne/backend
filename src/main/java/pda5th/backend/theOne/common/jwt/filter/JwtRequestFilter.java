package pda5th.backend.theOne.common.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pda5th.backend.theOne.common.jwt.util.JwtUtil;
import pda5th.backend.theOne.common.security.CustomUserDetailsService;
import pda5th.backend.theOne.common.security.UserPrincipal;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService; // CustomUserDetailsService 사용

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            // 1. 요청에서 JWT 추출
            String jwt = extractJwtFromRequest(request);

            // 2. JWT가 존재하고 SecurityContext에 인증 정보가 없는 경우
            if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtUtil.extractUsername(jwt); // JWT에서 사용자 이름 추출

                if (username != null) {
                    UserPrincipal userPrincipal = (UserPrincipal) customUserDetailsService.loadUserByUsername(username);

                    // 3. JWT 유효성 검증
                    if (jwtUtil.validateToken(jwt, userPrincipal.getUsername())) {
                        setAuthentication(userPrincipal, request); // 인증 설정
                    }
                }
            }
        } catch (Exception e) {
            // 예외 발생 시 401 상태 코드와 에러 메시지 반환
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
            return; // 필터 체인 중단
        }

        // 다음 필터로 요청 전달
        chain.doFilter(request, response);
    }

    /**
     * 1. Authorization 헤더에서 JWT 추출
     *
     * @param request HttpServletRequest 객체
     * @return JWT 문자열 또는 null
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // "Bearer " 이후의 JWT 반환
        }

        return null; // 유효한 JWT가 없는 경우 null 반환
    }

    /**
     * 2. SecurityContext에 인증 정보 설정
     *
     * @param userPrincipal 사용자 인증 정보
     * @param request HttpServletRequest 객체
     */
    private void setAuthentication(UserPrincipal userPrincipal, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
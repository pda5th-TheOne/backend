package pda5th.backend.theOne.common.jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // application.yml에서 주입된 값
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    // 비밀 키를 Key 객체로 변환 (최신 JJWT에서 권장)
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * 사용자 이름(Username)을 기반으로 JWT 토큰을 생성
     * @param username 사용자 이름
     * @return 생성된 JWT 토큰
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * JWT 토큰 생성 메서드
     * @param claims 클레임(Claims)
     * @param subject 사용자 식별 정보 (주로 사용자 이름)
     * @return 생성된 JWT 토큰
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 최신 방식: Key 객체와 알고리즘 설정
                .compact();
    }

    /**
     * JWT 토큰에서 사용자 이름(Username)을 추출
     * @param token JWT 토큰
     * @return 사용자 이름
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * JWT 토큰에서 만료 시간을 추출
     * @param token JWT 토큰
     * @return 토큰 만료 시간
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * JWT 토큰에서 특정 클레임(Claim)을 추출
     * @param token JWT 토큰
     * @param claimsResolver 클레임을 추출하는 함수
     * @return 추출된 클레임 값
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * JWT 토큰에서 모든 클레임(Claims)을 추출
     * @param token JWT 토큰
     * @return 클레임 객체
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 최신 방식: Key 객체로 서명 검증
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * JWT 토큰이 만료되었는지 확인
     * @param token JWT 토큰
     * @return 만료 여부 (true: 만료됨, false: 유효함)
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * JWT 토큰의 유효성을 검증
     * @param token JWT 토큰
     * @param username 검증 대상 사용자 이름
     * @return 유효 여부 (true: 유효함, false: 유효하지 않음)
     */
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
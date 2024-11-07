package pda5th.backend.theOne.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 유저ID

    @Column(nullable = false, length = 50)
    private String name; // 이름

    @Column(unique = true, nullable = false, length = 100)
    private String email; // 이메일

    private String profileImageUrl; // 프로필 이미지 URL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 역할 (ADMIN, USER)

    @Column(updatable = false)
    private LocalDateTime createdAt; // 생성 날짜

    @ManyToOne(fetch = FetchType.LAZY) // Lazy Loading 적용
    @JoinColumn(name = "p_id", nullable = false)
    private Provider provider; // 인증 방식

    // 주요 데이터만 설정하는 생성자 제공
    @Builder
    public User(String name, String email, String profileImageUrl, Role role, Provider provider) {
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.provider = provider;
        this.createdAt = LocalDateTime.now(); // 생성 시간 자동 설정
    }
}
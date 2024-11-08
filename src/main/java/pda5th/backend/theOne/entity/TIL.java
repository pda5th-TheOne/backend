package pda5th.backend.theOne.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tils")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TIL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // TIL ID

    private String title; // TIL 제목

    private String link; // TIL 링크

    private LocalDateTime createdAt; // 생성 날짜

    private LocalDateTime updatedAt; // 수정 날짜

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 유저 ID 참조

    @ManyToOne
    @JoinColumn(name = "board_id")
    private DailyBoard dailyBoard; // 게시판 ID 참조

    @Builder
    public TIL(String title, String link, User user) {
        this.title = title;
        this.link = link;
        this.user = user;
        this.createdAt = LocalDateTime.now(); // 생성 시간 자동 설정
        this.updatedAt = LocalDateTime.now(); // 생성 시간 자동 설정
    }

}
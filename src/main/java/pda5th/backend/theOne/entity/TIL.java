package pda5th.backend.theOne.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

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

    private LocalDate createdAt; // 생성 날짜

    private LocalDate updatedAt; // 수정 날짜

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 유저 ID 참조

    @ManyToOne
    @JoinColumn(name = "board_id")
    private DailyBoard dailyBoard; // 게시판 ID 참조
}
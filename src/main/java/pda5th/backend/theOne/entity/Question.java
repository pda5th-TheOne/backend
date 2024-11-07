package pda5th.backend.theOne.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 질문 댓글 ID

    private LocalDate createdAt; // 작성 날짜

    private LocalDate updatedAt; // 수정 날짜

    private String content; // 질문 댓글 내용

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 유저 ID 참조

    @ManyToOne
    @JoinColumn(name = "board_id")
    private DailyBoard dailyBoard; // 게시판 ID 참조
}
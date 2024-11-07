package pda5th.backend.theOne.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "replies")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 답글 ID

    private LocalDate createdAt; // 생성 날짜

    private String updatedAt; // 수정 날짜 (Domain 필드로 표현)

    private String content; // 답글 내용

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question; // 질문 댓글 ID 참조

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 유저 ID 참조
}
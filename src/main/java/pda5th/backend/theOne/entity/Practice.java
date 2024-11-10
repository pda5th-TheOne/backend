package pda5th.backend.theOne.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "practices")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Practice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 실습 ID

    private String title; // 실습 제목

    private String assignment; // 실습 문제

    private String answer; // 모델 답안

    private LocalDate createdAt; // 생성 날짜

    @ManyToOne
    @JoinColumn(name = "board_id")
    //순환참조를 끊기 위해 추가
    @JsonBackReference
    private DailyBoard dailyBoard; // 게시판 ID 참조

    @OneToMany(mappedBy = "practice", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsersPractices> usersPractices; // 연관된 UsersPractices -> cascade 삭제를 위함
}
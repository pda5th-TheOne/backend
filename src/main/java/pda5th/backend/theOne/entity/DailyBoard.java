package pda5th.backend.theOne.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "daily_boards")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DailyBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 게시판 ID

    private LocalDate createdAt; // 생성 날짜

    private String topic; // 수업 주제

    @OneToMany(mappedBy = "dailyBoard", cascade = CascadeType.ALL)
    private List<Practice> practices;

    @OneToMany(mappedBy = "dailyBoard", cascade = CascadeType.ALL)
    private List<TIL> tils;

    @OneToMany(mappedBy = "dailyBoard", cascade = CascadeType.ALL)
    private List<Question> questions;
}
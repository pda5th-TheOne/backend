package pda5th.backend.theOne.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "users_practices",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"prac_id", "user_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UsersPractices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 실습 완료한 사람 ID

    private LocalDate createdAt; // 생성 날짜

    @ManyToOne
    @JoinColumn(name = "prac_id")
    private Practice practice; // 실습 ID 참조

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 유저 ID 참조
}
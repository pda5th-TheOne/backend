package pda5th.backend.theOne.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "honey_goals")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class HoneyGoals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 꿈 목표치 ID

    private LocalDate createdAt; // 생성 날짜

    private Integer goal; // 목표치
}
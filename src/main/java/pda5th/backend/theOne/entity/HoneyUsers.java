package pda5th.backend.theOne.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "honey_users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class HoneyUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 매주 목표치 달성량 ID

    private Integer result; // 달성치

    @ManyToOne
    @JoinColumn(name = "id2")
    private HoneyGoals honeyGoal; // 목표치 ID 참조

    @ManyToOne
    @JoinColumn(name = "id3")
    private User user; // 유저 ID 참조
}
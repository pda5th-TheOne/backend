package pda5th.backend.theOne.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "provider")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 인증방식ID

    private String type; // 인증 타입 (Google 등)

    private String clientId; // 클라이언트 ID

    private String clientSecretKey; // 클라이언트 Secret Key
}
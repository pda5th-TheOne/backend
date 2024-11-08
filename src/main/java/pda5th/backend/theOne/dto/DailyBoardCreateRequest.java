package pda5th.backend.theOne.dto;

import java.time.LocalDate;

public record DailyBoardCreateRequest(LocalDate createdAt, String topic) {
}
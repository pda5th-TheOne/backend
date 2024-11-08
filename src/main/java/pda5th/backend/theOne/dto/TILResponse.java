package pda5th.backend.theOne.dto;

import java.time.LocalDateTime;

public record TILResponse(
        Integer id,
        String title,
        String link,
        Integer userId
) {}

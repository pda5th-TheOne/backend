package pda5th.backend.theOne.dto;

//사용자 인증 요청을 위한 DTO
public record AuthRequest(String username, String password) {}
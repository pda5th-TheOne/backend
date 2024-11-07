package pda5th.backend.theOne.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pda5th.backend.theOne.dto.PracticeResponseDto;
import pda5th.backend.theOne.service.PracticeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/practices")
public class PracticeController {
    private final PracticeService practiceService;

    @GetMapping("/{id}")
    public ResponseEntity<PracticeResponseDto> getPracticeDetail(@PathVariable Integer id) {
        PracticeResponseDto response = practiceService.getPracticeDetail(id);
        return ResponseEntity.ok(response);
    }
}

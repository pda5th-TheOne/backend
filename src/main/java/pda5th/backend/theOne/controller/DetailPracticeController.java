package pda5th.backend.theOne.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.dto.PracticeResponseDto;
import pda5th.backend.theOne.service.DetailPracticeService;
import pda5th.backend.theOne.service.PracticeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")

public class DetailPracticeController {
    private final DetailPracticeService detailPracticeService;

    @PostMapping("/boards/{id}/practices")
    public ResponseEntity<String> createDetailPractice(@PathVariable Integer id, @RequestBody String title) {
        String successTitle = detailPracticeService.createDetailPractice(id, title);

        return ResponseEntity.ok("success: " + successTitle);
    }

    // practiceId는 유일하니까 ..
    @PutMapping("/practices/{practiceId}")
    public ResponseEntity<String> updatePracticeTitle(
            @PathVariable Integer practiceId,
            @RequestBody String newTitle) {

        String updatedTitle = detailPracticeService.updatePracticeTitle(practiceId, newTitle);

        return ResponseEntity.ok("Updated title: " + updatedTitle);
    }

}

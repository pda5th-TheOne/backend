package pda5th.backend.theOne.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.common.security.UserPrincipal;
import pda5th.backend.theOne.dto.PracticeResponseDto;
import pda5th.backend.theOne.entity.User;
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

    @PutMapping("/{id}/assignment")
    public ResponseEntity<String> updateAssignment(
            @PathVariable Integer id,
            @RequestBody String newAssignment) {
        String updatedAssignment = practiceService.updateAssignment(id, newAssignment);

        return ResponseEntity.ok("Updated assignment: " + updatedAssignment);
    }

    @PutMapping("{id}/answer")
    public ResponseEntity<String> updateAnswer(
            @PathVariable Integer id,
            @RequestBody String newAnswer) {
        String updatedAnswer = practiceService.updateAnswer(id, newAnswer);

        return ResponseEntity.ok("updated answer: " + updatedAnswer);
    }

    @PostMapping("{id}/users_practices")
    public ResponseEntity<String> createUser(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userPrincipal.getUser();
        String submitUserName = practiceService.createSubmitUser(id, user);

        return ResponseEntity.ok("submit user: " + submitUserName);
    }
}

package pda5th.backend.theOne.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pda5th.backend.theOne.dto.TILCreateRequest;
import pda5th.backend.theOne.dto.TILResponse;
import pda5th.backend.theOne.dto.TILUpdateRequest;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.service.TILService;
import pda5th.backend.theOne.common.security.UserPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TILController {
    private final TILService tilService;

    // TIL 생성
    @PostMapping("/boards/{boardId}/TILs")
    @Operation(summary = "새로운 TIL 생성", description = "TIL 제목, 링크를 입력받아 새로운 TIL을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TIL 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<TILResponse> createTIL(@PathVariable Integer boardId, @RequestBody TILCreateRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        // TIL 생성 로직
        TILResponse tilResponse = tilService.createTIL(boardId,userPrincipal.getUser(), request);

        // 생성된 TIL 정보를 포함한 응답 반환
        return ResponseEntity.status(201).body(tilResponse);
    }

    // TIL 조회
    @GetMapping("/boards/{boardId}/TILs")
    @Operation(summary = "TIL 조회", description = "Board ID로 해당 TIL을 조회합니다.")
    public ResponseEntity<List<TILResponse>> getTIL(@PathVariable Integer boardId) {
        List<TILResponse> tilResponse = tilService.getTILById(boardId);
        return tilResponse != null ? ResponseEntity.ok(tilResponse) : ResponseEntity.notFound().build();
    }

    // TIL 수정
    @PutMapping("/TILs/{id}")
    @Operation(summary = "TIL 수정", description = "TIL ID로 해당 TIL을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TIL 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "TIL 미발견", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<TILResponse> updateTIL(@PathVariable Integer id, @RequestBody TILUpdateRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();

        TILResponse tilResponse = tilService.updateTIL(id, user, request);
        return tilResponse != null ? ResponseEntity.ok(tilResponse) : ResponseEntity.notFound().build();
    }

    // TIL 삭제
    @DeleteMapping("/TILs/{id}")
    @Operation(summary = "TIL 삭제", description = "TIL ID로 해당 TIL을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TIL 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "TIL 미발견", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<Void> deleteTIL(@PathVariable Integer id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();

        boolean deleted = tilService.deleteTIL(id, user);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

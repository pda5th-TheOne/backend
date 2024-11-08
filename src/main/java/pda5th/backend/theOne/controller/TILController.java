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
import pda5th.backend.theOne.service.TILService;
import pda5th.backend.theOne.common.security.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/TILs")
public class TILController {
    private final TILService tilService;

    // TIL 생성
    @PostMapping("")
    @Operation(summary = "새로운 TIL 생성", description = "TIL 제목, 링크를 입력받아 새로운 TIL을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TIL 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<TILResponse> createTIL(@RequestBody TILCreateRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        // TIL 생성 로직
        TILResponse tilResponse = tilService.createTIL(userPrincipal.getUser(), request);

        // 생성된 TIL 정보를 포함한 응답 반환
        return ResponseEntity.status(201).body(tilResponse);
    }

    // TIL 조회
    @GetMapping("/{id}")
    @Operation(summary = "TIL 조회", description = "TIL ID로 해당 TIL을 조회합니다.")
    public ResponseEntity<TILResponse> getTIL(@PathVariable Integer id) {
        TILResponse tilResponse = tilService.getTILById(id);
        return tilResponse != null ? ResponseEntity.ok(tilResponse) : ResponseEntity.notFound().build();
    }

    // TIL 수정
    @PutMapping("/{id}")
    @Operation(summary = "TIL 수정", description = "TIL ID로 해당 TIL을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TIL 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "TIL 미발견", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<TILResponse> updateTIL(@PathVariable Integer id, @RequestBody TILUpdateRequest request) {
        TILResponse tilResponse = tilService.updateTIL(id, request);
        return tilResponse != null ? ResponseEntity.ok(tilResponse) : ResponseEntity.notFound().build();
    }

    // TIL 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "TIL 삭제", description = "TIL ID로 해당 TIL을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TIL 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "TIL 미발견", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<Void> deleteTIL(@PathVariable Integer id) {
        boolean deleted = tilService.deleteTIL(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

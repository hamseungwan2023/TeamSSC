package com.sparta.teamssc.domain.team.weekProgress.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressRequestDto;
import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressResponseDto;
import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressUpdateRequestDto;
import com.sparta.teamssc.domain.team.weekProgress.entity.WeekProgress;
import com.sparta.teamssc.domain.team.weekProgress.service.WeekProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/weekProgress")
@RequiredArgsConstructor
public class WeekProgressController {

    private final WeekProgressService weekProgressService;

    // 주차 생성
    @PostMapping
    public ResponseEntity<ResponseDto<WeekProgressResponseDto>> createWeekProgress(@RequestBody WeekProgressRequestDto requestDto) {

        WeekProgress weekProgress = weekProgressService.createWeekProgress(requestDto.getName());
        WeekProgressResponseDto responseDto = WeekProgressResponseDto.builder()
                .id(weekProgress.getId())
                .name(weekProgress.getName())
                .status(weekProgress.getStatus())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.<WeekProgressResponseDto>builder()
                        .message("주차 상태 생성에 성공했습니다.")
                        .data(responseDto)
                        .build());
    }

    // 주차 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<WeekProgressResponseDto>> updateWeekProgress(@PathVariable Long id,
                                                                                   @RequestBody WeekProgressUpdateRequestDto request) {
        WeekProgressResponseDto weekProgress = weekProgressService.updateWeekProgress(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<WeekProgressResponseDto>builder()
                        .message("주차 상태 수정에 성공했습니다.")
                        .data(weekProgress)
                        .build());
    }
    // 상태수정
    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseDto<WeekProgressResponseDto>> updateWeekProgressStatus(@PathVariable Long id,
                                                                                         @RequestBody WeekProgressUpdateRequestDto request) {
        WeekProgressResponseDto weekProgress = weekProgressService.updateWeekProgressStatus(id, request.getStatus());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<WeekProgressResponseDto>builder()
                        .message("주차 상태 수정에 성공했습니다.")
                        .data(weekProgress)
                        .build());
    }

    // 주차 삭제하기 - 주차의 상태에 따라 삭제가 다름
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deleteWeekProgress(@PathVariable Long id) {
        weekProgressService.deleteWeekProgress(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto<>(null, "주차 상태가 삭제되었습니다."));
    }

    // 전체 주차 보기 - 페이징 처리
    @GetMapping
    public ResponseEntity<ResponseDto<Page<WeekProgressResponseDto>>> getAllWeekProgress(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<WeekProgress> weekProgressPage = weekProgressService.getAllWeekProgress(PageRequest.of(page, size));
        Page<WeekProgressResponseDto> responseDtos = weekProgressPage.map(weekProgress -> WeekProgressResponseDto.builder()
                .id(weekProgress.getId())
                .name(weekProgress.getName())
                .status(weekProgress.getStatus())
                .build());
        return ResponseEntity.ok(ResponseDto.<Page<WeekProgressResponseDto>>builder()
                .message("전체 주차 상태 조회에 성공했습니다.")
                .data(responseDtos)
                .build());
    }
}
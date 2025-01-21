package com.sopt.bbangzip.domain.study.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.common.dto.ResponseDto;
import com.sopt.bbangzip.domain.badge.api.dto.response.BadgeResponse;
import com.sopt.bbangzip.domain.study.api.dto.request.StudyCreateRequestDto;
import com.sopt.bbangzip.domain.study.api.dto.response.CreateStudyResponse;
import com.sopt.bbangzip.domain.study.service.StudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @PostMapping("/studies")
    public ResponseEntity<CreateStudyResponse> createStudy(
            @UserId final long userId,
            @RequestBody @Valid final StudyCreateRequestDto studyCreateRequestDto
    ) {
        CreateStudyResponse response = studyService.createStudy(userId, studyCreateRequestDto);
        return ResponseEntity.ok(ResponseDto.success(response).data());
    }
}

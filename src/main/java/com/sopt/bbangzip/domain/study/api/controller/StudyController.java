package com.sopt.bbangzip.domain.study.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.domain.study.api.dto.request.StudyCreateRequestDto;
import com.sopt.bbangzip.domain.study.api.dto.response.StudyCreateResponseDto;
import com.sopt.bbangzip.domain.study.service.StudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @PostMapping("/studies")
    public ResponseEntity<Void> createStudy(
            @UserId final long userId,
            @RequestBody @Valid final StudyCreateRequestDto studyCreateRequestDto
    ) {
        studyService.createStudy(userId, studyCreateRequestDto);
        return ResponseEntity.noContent().build();
    }

}

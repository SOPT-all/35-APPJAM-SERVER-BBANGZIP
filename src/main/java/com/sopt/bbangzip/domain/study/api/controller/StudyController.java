package com.sopt.bbangzip.domain.study.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.common.dto.ResponseDto;
import com.sopt.bbangzip.domain.study.api.dto.request.StudyCreateRequestDto;
import com.sopt.bbangzip.domain.study.api.dto.response.StudyCreateResponseDto;
import com.sopt.bbangzip.domain.study.service.StudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @PostMapping("/studies")
    public ResponseEntity<StudyCreateResponseDto> createStudy(
            @UserId final Long userId,
            @RequestBody @Valid final StudyCreateRequestDto studyCreateRequestDto
    ) {
        StudyCreateResponseDto responseDto = studyService.createStudy(userId, studyCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}

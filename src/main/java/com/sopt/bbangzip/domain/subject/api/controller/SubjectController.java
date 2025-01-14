package com.sopt.bbangzip.domain.subject.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.common.dto.ResponseDto;
import com.sopt.bbangzip.common.exception.base.DuplicateSubjectException;
import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectCreateDto;
import com.sopt.bbangzip.domain.subject.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    // 과목 추가하기 API
    @PostMapping("/subjects")
    public ResponseEntity<ResponseDto<Void>> createSubject(
            @UserId final Long userId,
            @RequestBody @Valid final SubjectCreateDto subjectCreateDto
    ) {
        subjectService.createSubject(userId, subjectCreateDto);
        return ResponseEntity.noContent().build();
    }
}
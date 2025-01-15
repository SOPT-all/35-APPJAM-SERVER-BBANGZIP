package com.sopt.bbangzip.domain.subject.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.common.dto.ResponseDto;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectCreateDto;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectDeleteDto;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectNameOrMotivationMessageDto;
import com.sopt.bbangzip.domain.subject.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    // 과목 삭제하기 API
    @DeleteMapping("/subjects")
    public ResponseEntity<Void> deleteSubject(
            @UserId final Long userId,
            @RequestBody @Valid final SubjectDeleteDto subjectDeleteDto
    ) {
        subjectService.deleteSubject(userId, subjectDeleteDto);
        return ResponseEntity.noContent().build();
    }

     // 과목명 및 과목 별 동기부여 메세지 작성 및 수정 API
    @PutMapping("/subjects/{subjectId}/{options}")
    public ResponseEntity<Void> updateMotivationMessage(
            @UserId final Long userId,
            @PathVariable final Long subjectId,
            @PathVariable final String options,
            @RequestBody @Valid final SubjectNameOrMotivationMessageDto subjectNameOrMotivationMessageDto
            ) {

        subjectService.updateSubjectNameOrMotivationMessage(userId, subjectId, options, subjectNameOrMotivationMessageDto.value());
        return ResponseEntity.noContent().build();
    }

}